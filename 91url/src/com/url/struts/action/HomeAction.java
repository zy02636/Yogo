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
	 * ��װ���session��request
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
	 * HTMLҳ��Ĳ���,�Զ���װ
	 */
	// url��ַ
	private String url;
	public void setUrl(String url) {
		this.url = url;

	}

	// ҳ��
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
	// �û�ͷ��
	private static final int BUFFER_SIZE = 2 * 1024;//����ϴ�2MB��ͼƬ
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
	//�����ʵ��û���ID
	private int id;
	public void setId(int id) {
		this.id = id;
	}
	/*
	 * ���÷���ֵ��ȷ����ת���ĸ�ҳ��
	 */
	public String addUrl() throws Exception {
		try {
			User user = (User) session.get("user");
			if (user != null && (user.getUserId() == userId)) {
				ActionContext actionContext = ActionContext.getContext();
				Map session = actionContext.getSession();
				Url url = new Url();
				url.setUrl(this.url);

				// �����¼�û���actionUrl��ֱ�ӷ���,��ת��
				if (url.getUrl().equals("http://www.91url.com")) {
					return "FAIL";
				} else {
					Calendar cal = Calendar.getInstance();
					Date time = cal.getTime();
					String curDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);

					url.setAddDate(curDate);
					url.setUrlOwner(user.getNickName());
					url.setUrlTitle("91url.com ��ǩ");
					url.setUrlOwnerId(user.getUserId());
					url.setUrlType(urlType);// Ĭ��Ϊ��������ǩ
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
				return "LOGIN"; // δ��¼�û��ķ���ֱ����ת����¼ҳ��
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
			// ���ñ��뷽ʽ
			parser.setEncoding(encode);
			// ��ȡUrl��Title
			NodeFilter urlTitle = new TagNameFilter("title");

			// ���˳�������Ϣ
			NodeList nodelist = parser.parse(urlTitle);
			Node node_urlTitle = nodelist.elementAt(0);
			// ��ȡ���������Ϣ
			if (node_urlTitle != null) {
				String title = node_urlTitle.toPlainTextString();
				if (title.length() >= 70) {
					url.setUrlTitle(title.substring(0, 70) + "...");
				} else {
					url.setUrlTitle(title);
				}
			} else {
				url.setUrlTitle("91url.com ��ǩ");
			}
			parser.reset();
		} catch (Exception e) {
			// �����ظ��ݹ�
			if (!encode.equals("gb2312")) {
				extractWeb(url, "gb2312");
			}
			System.out.println("��ҳ�������: com.url.struts.action.UrlAction line 178");
		}
	}
	/*
	 * ��ȡsession�е�User��Ϣ,��ȡ��ʼ���û���ǩ��ҳ����Ϣ
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
	 * ��ȡsession�е�User��Ϣ,��ȡ��ʼ��չʾ�û�������Ϣ����ҳ
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
				
				request.put("validate", "processed");//��ֹ��¼���û�ֱ�ӷ���jspҳ��
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
	 * �����û�ID���������û�ҳ��
	 */
	public String getProfile() throws Exception {
		try {
			User user = (User) session.get("user");
			//�жϷ��ʵ��û��Ƿ������ҷ���
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
					request.put("relationType", 1);//1�����Ѿ��Ը��û����й�ע
				}else{
					request.put("relationType", 0);//0����δ�Ը��û����й�ע
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
			} else if(user != null && (user.getUserId() == id)){ //��¼�û����Լ���ID
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
	 * �������url�б�,�����ַ���;
	 */
	public String getUrlsByPlugin() throws Exception {
		try {
			User user = (User) session.get("user");
			HttpServletResponse response = ServletActionContext.getResponse();
			
			//ajax ������Ϣ��Ҫ����
			String info = "";
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			
			if (user != null || (user.getUserId() == userId)) {
				List<Url> urls = urlService.getUrlsByUserIdAndIndex(userId,pageNum);

				StringBuffer sb = new StringBuffer();
				for (Url url : urls) {
					//url.getUrl().replace("&_",""); ��,����.���ص�bug
					//url.getUrlTitle().replace("&__", "");//Ϊ�˷�ֹjs��ȡ�ı�ʱ������,��������.
					sb.append(url.getUrl() + "&_" + url.getUrlTitle() + "&__");
				}
				
				info = sb.toString();
				out.write(info);
				out.close();
				return null;
			} else {
				info = "��������,��ѯʧ��";
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
	 * �����û���ͷ��
	 */
	public String updateUserImg() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String phsicalDirectory = "";
		try {
			User user = (User) session.get("user");
            //����Ͳ�ƥ��session�е�userId�������е�userId
			if (user != null) {
				phsicalDirectory = ServletActionContext.getServletContext().getRealPath("/img");
				
	            BufferedImage   sourceImg   =   javax.imageio.ImageIO.read(new FileInputStream(userImg)); //��ͼƬ�����ڴ�
				StringBuffer sb = new StringBuffer();//���ڴ洢���ص���Ϣ
				
				int height = sourceImg.getHeight();
				int width = sourceImg.getWidth();
				float size = (float)userImg.length()/1024;//��λKB
				
				float wh = (float)width/height;//��͸ߵı���
				//��͸ߵı��ʳ���Լ�ƽ�ָ����,����ͼƬ�ߴ���Сֵ�����ж�
				if(wh > 1.65 || height < 50){
					sb.append("<font style='color:red;font-size:12px'>������ͼƬ�ĸ߶�</font>");
				}else if(wh < 0.55 || width < 50){
					sb.append("<font style='color:red;font-size:12px'>������ͼƬ�Ŀ��</font>");
				}else if(size > (2*1024)){ //ͼƬ����
					sb.append("<font style='color:red;font-size:12px'>�ϴ�ͼƬ���ܴ���2MB</font>");
				}else{
					/*
					 * �����Ǵ�����Ϲ涨��߱�����ͼƬ
					 */
					String imgExtention = getExtention(fileName);//��ȡͼƬ����չ��
					String imageFileName = new Date().getTime() + imgExtention;//������һ��ͼƬ����
					
                    //���ϴ�ͼƬ���вü�,�γ�һ��������,���1:1,�ٽ�������
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
					
					String sDstFilePath = phsicalDirectory+"/cmpuser/small_"+imageFileName;//Сͷ��File��·��  
					String tmpSDstFilePath = phsicalDirectory+"/cmpuser/temp_small_"+imageFileName;//Сͷ��File��·��  
					cut(userImg.getAbsolutePath(),tmpSDstFilePath,imgPos[0],imgPos[1],imgPos[2],imgPos[2],imgExtention.replace(".",""));//��ȡ��߱�Ϊ 1:1��ͼƬ 
					cmpImg(tmpSDstFilePath,sDstFilePath,50,50);//�Խ�ȡ���ͼƬ����ѹ��
					
					File tmpSDstFile = new File(tmpSDstFilePath);
					if(tmpSDstFile.exists()){
						tmpSDstFile.delete();
					}
					if(width>=228){
						//��ͼƬ��ȴ�����ʾͼƬ��Ŀ��ʱ,��ͼƬ��СΪ228px���ͼƬ,�ٸ��ݱ�������߶�
						float rate = (float)228/width; //��ȡͼƬ���ű���
						float fheight = (float)rate * height; //�޸�ͼƬ�ĸ߶�
						height = (int)fheight;
						width = 228;
					}
					//Ӳ���ϵ�����·��,���ڴ��ͷ��
					String bDstFilePath = phsicalDirectory+"/user/big_"+imageFileName;//��ͷ��File��·��  
					//���ϴ�ͼƬ��������,����ͼƬ,���ɴ�ͷ��
					cmpImg(userImg.getAbsolutePath(),bDstFilePath,width,height);

					String formalBigImageFilePath = user.getBigImagePath();//ԭ�ȵ�ͼƬ·��
					//����ж�����˵��ͷ��Ϊ�ǵ�һ���޸�Ĭ��ͼƬ
					if(formalBigImageFilePath.indexOf("img/user/")==0){
						File formalBigUserImage = new File(ServletActionContext.getServletContext().getRealPath(user.getBigImagePath()));//���ݿ�洢��ͼƬ���·��
						if(formalBigUserImage.exists()){
							formalBigUserImage.delete();//��ɾ��ԭ���Ĵ�ͷ��
						}
					}
					user.setBigImagePath("img/user/big_"+imageFileName);
					user.setCmpImagePath("img/cmpuser/small_"+imageFileName);
					userService.updateUser(user);//�����û�ͷ�� 
				}
				sb.append("�ϴ�ͷ��ɹ�!");

				out.println("<script>window.parent.callBackChangeUserImage('"+user.getBigImagePath()+"','"+sb.toString()+"','"+width+"','"+height+"');</script>");
				return null;
			}else{
				out.println("<font style='font-size:12px;color:red'>��������,����ͷ��ʧ��</font>");
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
	 * ��ȡͼƬ��չ��
	 * @param fileName �ļ���
	 * @return
	 */
	private String getExtention(String fileName){  
        int pos = fileName.lastIndexOf(".");  
        return fileName.substring(pos);  
    }
	/**
     * ѹ��ͼƬ��С,��Ϊ��ͷ��
     * @param imgsrc �ļ�·��
     * @param imgdist ���·��
     * @param widthdist ���
     * @param heightdist �߶�
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
	 * ��ͼƬ�ü�,��Ϊ����1:1��ͼƬ,Ȼ�����ŵ�50px:50px
	 * @param srcFile Դ�ļ�·��
	 * @param dstFile Ŀ���ļ�·��
	 * @param x ������
	 * @param y  ������
	 * @param width ��� 
	 * @param height �߶�
	 * @param imgType ͼƬ����
	 * @throws IOException
	 */
	public void cut(String srcPath, String subPath, int x, int y, int width,int height,String imgType) throws IOException {
		FileInputStream is = null;
		ImageInputStream iis = null;
		try {
			// ��ȡͼƬ�ļ�
			is = new FileInputStream(srcPath);
			/*
			 * ���ذ������е�ǰ��ע�� ImageReader �� Iterator����Щ ImageReader �����ܹ�����ָ����ʽ��
			 * ������formatName - ��������ʽ��ʽ���� .������ "jpeg" �� "tiff"���� ��
			 */
			Iterator<ImageReader> it = ImageIO
					.getImageReadersByFormatName(imgType);
			ImageReader reader = it.next();
			// ��ȡͼƬ��
			iis = ImageIO.createImageInputStream(is);
			/*
			 * <p>iis:��ȡԴ.true:ֻ��ǰ���� </p>.�������Ϊ ��ֻ��ǰ��������
			 * ��������ζ�Ű���������Դ�е�ͼ��ֻ��˳���ȡ���������� reader ���⻺���������ǰ�Ѿ���ȡ��ͼ����������ݵ���Щ���벿�֡�
			 */
			reader.setInput(iis, true);
			/*
			 * <p>������ζ������н������<p>.����ָ�����������ʱ�� Java Image I/O
			 * ��ܵ��������е���ת��һ��ͼ���һ��ͼ�������ض�ͼ���ʽ�Ĳ�� ������ ImageReader ʵ�ֵ�
			 * getDefaultReadParam �����з��� ImageReadParam ��ʵ����
			 */
			ImageReadParam param = reader.getDefaultReadParam();
			/*
			 * ͼƬ�ü�����Rectangle ָ��������ռ��е�һ������ͨ�� Rectangle ����
			 * �����϶�������꣨x��y������Ⱥ͸߶ȿ��Զ����������
			 */
			Rectangle rect = new Rectangle(x, y, width, height);
			// �ṩһ�� BufferedImage���������������������ݵ�Ŀ�ꡣ
			param.setSourceRegion(rect);
			/*
			 * ʹ�����ṩ�� ImageReadParam ��ȡͨ������ imageIndex ָ���Ķ��󣬲��� ����Ϊһ��������
			 * BufferedImage ���ء�
			 */
			BufferedImage bi = reader.read(0, param);
			// ������ͼƬ
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
