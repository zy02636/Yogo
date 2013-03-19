package com.url.struts.action;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.url.service.ITagService;
import com.url.service.IUrlService;
import com.url.service.IUserService;
import com.url.service.IUserTagService;
import com.url.bean.Tag;
import com.url.bean.Url;
import com.url.bean.User;
import com.url.bean.UserTag;

public class HomeAction extends ActionSupport implements SessionAware,RequestAware{
	private IUrlService urlService;
	private IUserTagService userTagService;
	private ITagService tagService;
	private IUserService userService;

	public void setUrlService(IUrlService urlService) {
		this.urlService = urlService;
	}
	public void setUserTagService(IUserTagService userTagService) {
		this.userTagService = userTagService;
	}
	public void setTagService(ITagService tagService) {
		this.tagService = tagService;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	/*
	 * 封装后的session和request
	 */
	private Map session;

	public void setSession(Map session) {
		this.session = session;
	}

	private Map request;

	public void setRequest(Map request) {
		this.request = request;
	}

	/*
	 * HTML页面的参数,自动封装
	 */
	// url地址
	private String url;
	public void setUrl(String url) {
		this.url = url;

	}

	// 页数
	private int pageNum = 1;
	private int userId;
	private int urlType;
	public void setUrlType(int urlType) {
		this.urlType = urlType;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	// 用户头像
	private static final int BUFFER_SIZE = 2 * 1024;//最多上传2MB的图片
	private File userImg;
	private String contentType;
	private String fileName;
	private String imageFilePath;
	
	public void setUserImg(File userImg) {
		this.userImg = userImg;
	}
	public void setUserImgContentType(String contentType) {
		this.contentType = contentType;
	}
	public void setUserImgFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}
	//被访问的用户的ID
	private int id;
	public void setId(int id) {
		this.id = id;
	}
	/*
	 * 利用返回值来确定跳转到哪个页面
	 */
	public String addUrl() throws Exception {
		try {
			User user = (User) session.get("user");
			if (user != null && (user.getUserId() == userId)) {
				ActionContext actionContext = ActionContext.getContext();
				Map session = actionContext.getSession();
				Url url = new Url();
				url.setUrl(this.url);

				// 避免登录用户对actionUrl的直接访问,跳转到
				if (url.getUrl().equals("http://www.91url.com")) {
					return "FAIL";
				} else {
					Calendar cal = Calendar.getInstance();
					Date time = cal.getTime();
					String curDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);

					url.setAddDate(curDate);
					url.setUrlOwner(user.getNickName());
					url.setUrlTitle("91url.com 标签");
					url.setUrlOwnerId(user.getUserId());
					url.setUrlType(urlType);// 默认为公开的书签
					url.setUrlUserImage(user.getCmpImagePath());
					processURL(url);
					url.setUrlTitle(url.getUrlTitle().replace("|"," "));
					int result = urlService.addUrl(url);
					if (result >= 1) {
						String urlAddress = url.getUrl();
						String title = url.getUrlTitle().replace("|"," ");
						String addDate = url.getAddDate();
						int urlId = result;

						ServletActionContext.getResponse().setContentType(
								"text/plain");
						ServletActionContext.getResponse()
								.setCharacterEncoding("utf-8");
						ServletActionContext.getResponse().getWriter().println(
								urlAddress + "&_" + title + "&_" + addDate
										+ "&_" + urlId);
						ServletActionContext.getResponse().getWriter().close();
						return null;
					} else {
						return "FAIL";
					}
				}
			} else {
				return "LOGIN"; // 未登录用户的访问直接跳转到登录页面
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "FAIL";
		}
	}
	private void processURL(Url url) {
		String baseURL = url.getUrl();
		try {
			URL preUrl = new URL(baseURL);
			HttpURLConnection httpURLCon = (HttpURLConnection) preUrl.openConnection();
			httpURLCon.setConnectTimeout(5000);

			if (httpURLCon.getResponseCode() == 200) {
				extractWeb(url, "utf-8");
			} 
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
	private void extractWeb(Url url, String encode) {
		try {
			Parser parser = new Parser(url.getUrl());
			// 设置编码方式
			parser.setEncoding(encode);
			// 提取Url的Title
			NodeFilter urlTitle = new TagNameFilter("title");

			// 过滤出标题信息
			NodeList nodelist = parser.parse(urlTitle);
			Node node_urlTitle = nodelist.elementAt(0);
			// 提取问题标题信息
			if (node_urlTitle != null) {
				String title = node_urlTitle.toPlainTextString();
				if (title.length() >= 70) {
					url.setUrlTitle(title.substring(0, 70) + "...");
				} else {
					url.setUrlTitle(title);
				}
			} else {
				url.setUrlTitle("91url.com 书签");
			}
			parser.reset();
		} catch (Exception e) {
			// 避免重复递归
			if (!encode.equals("gb2312")) {
				extractWeb(url, "gb2312");
			}
			System.out.println("网页编码出错: com.url.struts.action.UrlAction line 178");
		}
	}
	/*
	 * 读取session中的User信息,获取初始化用户书签主页的信息
	 */
	public String Profile() throws Exception {
		try {
			User user = (User) session.get("user");
			if (user != null) {
				int userId = user.getUserId();
				List<Url> urls = urlService.getUrlsByUserId(userId,pageNum,0);
				List<UserTag> userTags = userTagService.getUserTagsByUserId(userId);
				List<Tag> tags = tagService.getTagsByUserId(userId);
				int fansSum = userService.getFansSum(userId);
				int followersSum = userService.getFollowersSum(userId);
				int urlsSum = urlService.getUrlSumByUserId(userId);
                
				request.put("validate", "processed");
				request.put("fansSum", fansSum);
				request.put("followersSum", followersSum);
				request.put("urlsSum", urlsSum);
				request.put("user", user);
				request.put("urls", urls);
				request.put("userTags", userTags);
				request.put("tags", tags);
				request.put("curPage", pageNum);
				pageNum = 1;
				return "SUCCESS";
			} else {
				return "FAIL";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "FAIL_404";
		}
	}
	/*
	 * 读取session中的User信息,获取初始化展示用户好友信息的主页
	 */
	public String initMain() throws Exception {
		try {
			User user = (User) session.get("user");
			if (user != null) {
				int userId = user.getUserId();

				List<Url> urls = urlService.getGuestUrlsByUserId(userId,pageNum);
				//System.out.println(pageNum);
				int fansSum = userService.getFansSum(userId);
				int followersSum = userService.getFollowersSum(userId);
				int urlsSum = urlService.getUrlSumByUserId(userId);
				int followUrlSum = urlService.getFollowsUrlSum(userId);
				List<UserTag> userTags = userTagService.getUserTagsByUserId(userId);
				List<Tag> tags = tagService.getTagsByUserId(userId);
				
				request.put("validate", "processed");//防止登录的用户直接访问jsp页面
				request.put("userTags", userTags);
				request.put("tags", tags);
				request.put("fansSum", fansSum);
				request.put("followersSum", followersSum);
				request.put("urlsSum", urlsSum);
				request.put("followUrlSum", followUrlSum);
				request.put("user", user);
				request.put("urls", urls);
				request.put("curPage", pageNum);
				pageNum = 1;
				return "SUCCESS";
			} else {
				return "FAIL";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "FAIL_404";
		}
	}
	
	/*
	 * 根据用户ID访问其他用户页面
	 */
	public String getProfile() throws Exception {
		try {
			User user = (User) session.get("user");
			//判断访问的用户是否是自我访问
			if (user != null && (user.getUserId() != id)) {
				User visitedUser = userService.getUser(id);
				List<Url> urls = urlService.getUrlsByUserId(id,pageNum,1);
				List<UserTag> userTags = userTagService.getUserTagsByUserId(id);
				List<Tag> tags = tagService.getTagsByUserId(id);
				int fansSum = userService.getFansSum(id);
				int followersSum = userService.getFollowersSum(id);
				int urlsSum = urlService.getUrlSumByUserId(id);

				boolean result = userService.checkRelation(id,user.getUserId());

				if(result){
					request.put("relationType", 1);//1代表已经对该用户进行关注
				}else{
					request.put("relationType", 0);//0代表还未对该用户进行关注
				}
				request.put("validate", "processed");
				request.put("fansSum", fansSum);
				request.put("followersSum", followersSum);
				request.put("urlsSum", urlsSum);
				request.put("user", visitedUser);
				request.put("urls", urls);
				request.put("userTags", userTags);
				request.put("tags", tags);
				request.put("curPage", pageNum);
				pageNum = 1;
				return "SUCCESS";
			} else if(user != null && (user.getUserId() == id)){ //登录用户点自己的ID
				return "SUCCESS_SELF";
			} else {
				return "FAIL";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "FAIL_404";
		}
	}

	/*
	 * 插件访问url列表,返回字符串;
	 */
	public String getUrlsByPlugin() throws Exception {
		try {
			User user = (User) session.get("user");
			HttpServletResponse response = ServletActionContext.getResponse();
			
			//ajax 返回信息需要的类
			String info = "";
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			
			if (user != null || (user.getUserId() == userId)) {
				List<Url> urls = urlService.getUrlsByUserIdAndIndex(userId,pageNum);

				StringBuffer sb = new StringBuffer();
				for (Url url : urls) {
					//url.getUrl().replace("&_",""); 哎,算了.隐藏的bug
					//url.getUrlTitle().replace("&__", "");//为了防止js读取文本时出问题,性能问题.
					sb.append(url.getUrl() + "&_" + url.getUrlTitle() + "&__");
				}
				
				info = sb.toString();
				out.write(info);
				out.close();
				return null;
			} else {
				info = "网络问题,查询失败";
				out.println(info);
				out.close();
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/*
	 * 更新用户的头像
	 */
	public String updateUserImg() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String phsicalDirectory = "";
		try {
			User user = (User) session.get("user");
            //这里就不匹配session中的userId跟参数中的userId
			if (user != null) {
				phsicalDirectory = ServletActionContext.getServletContext().getRealPath("/img");
				
	            BufferedImage   sourceImg   =   javax.imageio.ImageIO.read(new FileInputStream(userImg)); //将图片读入内存
				StringBuffer sb = new StringBuffer();//用于存储返回的信息
				
				int height = sourceImg.getHeight();
				int width = sourceImg.getWidth();
				float size = (float)userImg.length()/1024;//单位KB
				
				float wh = (float)width/height;//宽和高的比例
				//宽和高的比率超过约黄金分割比例,并对图片尺寸最小值进行判断
				if(wh > 1.65 || height < 50){
					sb.append("<font style='color:red;font-size:12px'>请增加图片的高度</font>");
				}else if(wh < 0.55 || width < 50){
					sb.append("<font style='color:red;font-size:12px'>请增加图片的宽度</font>");
				}else if(size > (2*1024)){ //图片过大
					sb.append("<font style='color:red;font-size:12px'>上传图片不能大于2MB</font>");
				}else{
					/*
					 * 以下是处理符合规定宽高比例的图片
					 */
					String imgExtention = getExtention(fileName);//获取图片的扩展名
					String imageFileName = new Date().getTime() + imgExtention;//随机组成一个图片名称
					
                    //对上传图片进行裁剪,形成一个正方形,宽高1:1,再进行缩放
					int[] imgPos = {0,0,0};//x,y,length
					if(width > height){
						imgPos[0] = (width-height)/2;
						imgPos[1] = 0;
						imgPos[2] = height;
					}else{
						imgPos[0] = 0;
						imgPos[1] = (height-width)/2;
						imgPos[2] = width;
					}
					
					String sDstFilePath = phsicalDirectory+"/cmpuser/small_"+imageFileName;//小头像File的路径  
					String tmpSDstFilePath = phsicalDirectory+"/cmpuser/temp_small_"+imageFileName;//小头像File的路径  
					cut(userImg.getAbsolutePath(),tmpSDstFilePath,imgPos[0],imgPos[1],imgPos[2],imgPos[2],imgExtention.replace(".",""));//截取宽高比为 1:1的图片 
					cmpImg(tmpSDstFilePath,sDstFilePath,50,50);//对截取后的图片进行压缩
					
					File tmpSDstFile = new File(tmpSDstFilePath);
					if(tmpSDstFile.exists()){
						tmpSDstFile.delete();
					}
					if(width>=228){
						//当图片宽度大于显示图片层的宽度时,将图片缩小为228px宽度图片,再根据比例求出高度
						float rate = (float)228/width; //获取图片缩放比例
						float fheight = (float)rate * height; //修改图片的高度
						height = (int)fheight;
						width = 228;
					}
					//硬盘上的物理路径,用于存大头像
					String bDstFilePath = phsicalDirectory+"/user/big_"+imageFileName;//大头像File的路径  
					//对上传图片进行缩放,加载图片,生成大头像
					cmpImg(userImg.getAbsolutePath(),bDstFilePath,width,height);

					String formalBigImageFilePath = user.getBigImagePath();//原先的图片路径
					//这个判断条件说明头像为非第一次修改默认图片
					if(formalBigImageFilePath.indexOf("img/user/")==0){
						File formalBigUserImage = new File(ServletActionContext.getServletContext().getRealPath(user.getBigImagePath()));//数据库存储的图片相对路径
						if(formalBigUserImage.exists()){
							formalBigUserImage.delete();//并删除原来的大头像
						}
					}
					user.setBigImagePath("img/user/big_"+imageFileName);
					user.setCmpImagePath("img/cmpuser/small_"+imageFileName);
					userService.updateUser(user);//更新用户头像 
				}
				sb.append("上传头像成功!");

				out.println("<script>window.parent.callBackChangeUserImage('"+user.getBigImagePath()+"','"+sb.toString()+"','"+width+"','"+height+"');</script>");
				return null;
			}else{
				out.println("<font style='font-size:12px;color:red'>网络问题,更新头像失败</font>");
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}finally{
			if(null != out){
				out.close();//response
			}
		}
	}
	/**
	 * 获取图片扩展名
	 * @param fileName 文件名
	 * @return
	 */
	private String getExtention(String fileName){  
        int pos = fileName.lastIndexOf(".");  
        return fileName.substring(pos);  
    }
	/**
     * 压缩图片大小,作为大头像
     * @param imgsrc 文件路径
     * @param imgdist 输出路径
     * @param widthdist 宽度
     * @param heightdist 高度
     */
	public void cmpImg(String imgsrc, String imgdist, int widthdist,int heightdist) {
		try {
			File srcfile = new File(imgsrc);
			if (!srcfile.exists()) {
				return;
			}
			Image src = javax.imageio.ImageIO.read(srcfile);

			BufferedImage tag = new BufferedImage((int) widthdist,(int) heightdist, BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist,Image.SCALE_SMOOTH), 0, 0, null);

			FileOutputStream out = new FileOutputStream(imgdist);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(tag);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 对图片裁剪,成为长宽1:1的图片,然后缩放到50px:50px
	 * @param srcFile 源文件路径
	 * @param dstFile 目标文件路径
	 * @param x 横坐标
	 * @param y  纵坐标
	 * @param width 宽度 
	 * @param height 高度
	 * @param imgType 图片类型
	 * @throws IOException
	 */
	public void cut(String srcPath, String subPath, int x, int y, int width,int height,String imgType) throws IOException {
		FileInputStream is = null;
		ImageInputStream iis = null;
		try {
			// 读取图片文件
			is = new FileInputStream(srcPath);
			/*
			 * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够解码指定格式。
			 * 参数：formatName - 包含非正式格式名称 .（例如 "jpeg" 或 "tiff"）等 。
			 */
			Iterator<ImageReader> it = ImageIO
					.getImageReadersByFormatName(imgType);
			ImageReader reader = it.next();
			// 获取图片流
			iis = ImageIO.createImageInputStream(is);
			/*
			 * <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。
			 * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
			 */
			reader.setInput(iis, true);
			/*
			 * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O
			 * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件 将从其 ImageReader 实现的
			 * getDefaultReadParam 方法中返回 ImageReadParam 的实例。
			 */
			ImageReadParam param = reader.getDefaultReadParam();
			/*
			 * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
			 * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
			 */
			Rectangle rect = new Rectangle(x, y, width, height);
			// 提供一个 BufferedImage，将其用作解码像素数据的目标。
			param.setSourceRegion(rect);
			/*
			 * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的
			 * BufferedImage 返回。
			 */
			BufferedImage bi = reader.read(0, param);
			// 保存新图片
			ImageIO.write(bi, imgType, new File(subPath));
		} finally {
			if (is != null) {
				is.close();
			}
			if (iis != null) {
				iis.close();
			}
		}
	}
}
