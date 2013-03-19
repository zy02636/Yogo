package com.url.socket;

import java.io.IOException;
import java.net.ServerSocket;


public class PythonServer {
	private ServerSocket ss;

	public PythonServer() {
		try {
			int i = 0;
			ss = new ServerSocket(10000);
			while (true) {
				i++;
				PythonReceiver t = new PythonReceiver(ss.accept(), i);
				Thread tt = new Thread(t);
				tt.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ss != null) {
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		  System.out.println("Start.");
		  new PythonServer();
	}
}
