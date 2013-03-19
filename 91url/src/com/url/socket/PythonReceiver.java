package com.url.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URLDecoder;

public class PythonReceiver implements Runnable {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private int i = 0;

	public PythonReceiver(Socket socket, int i) {
		this.socket = socket;
		this.i = i;
	}

	@Override
	public void run() {
		try {
			socket.setSoTimeout(4000);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));

			String msg = "";
			while((msg = in.readLine()) != null){
				msg = URLDecoder.decode(msg,"UTF-8");
				System.out.println(msg);
				if(msg.equals("end")){
					out = new PrintWriter(socket.getOutputStream());
					out.write("success");
					out.flush();
					socket.close();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public Socket getSocket() {
		return socket;
	}
}
