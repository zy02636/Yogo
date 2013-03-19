<%@ page autoFlush="false"  import="java.util.*,java.awt.*,java.awt.image.*,com.sun.image.codec.jpeg.*,java.util.*" pageEncoding="utf-8"%>
<body style="margin:0px;">
<%
     response.setContentType("image/jpeg");
     response.addHeader("pragma","NO-cache");
     response.addHeader("Cache-Control","no-cache");
     response.addDateHeader("Expries",0);
	
	 String chose="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	 char display[]={'0',' ','0',' ','0',' ','0'},ran[]={'0','0','0','0'},temp;
	
	 Random rand = new Random();
	
	 for(int i = 0 ; i < 4; i++)
	 {
		temp = chose.charAt(rand.nextInt(chose.length()));
		display[i*2] = temp;
		ran[i] = temp;
	 }
	 String random = String.valueOf(display);
	 session.setAttribute("pwdCode",String.valueOf(ran));//保存随机生成的字符以及数字
	
	 int width=120, height=30;
     BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
     Graphics g = image.getGraphics();
     //以下填充背景颜色
     g.setColor(Color.WHITE);
     g.fillRect(0,0,width,height);
    //设置字体颜色
     g.setColor(Color.black);
     Font font=new Font("Verdana",Font.BOLD,20);
     g.setFont(font);
     //g.drawString(random,5,14);
     g.drawString(random,5,20);
     g.dispose();
     ServletOutputStream outStream = response.getOutputStream();
     JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outStream);
     encoder.encode(image);
     outStream.close();
     out.clear();
     out = pageContext.pushBody();
%>
</body>
