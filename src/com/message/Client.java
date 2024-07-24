package com.message;


import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;


/**
 * 
 * @author wangjun04
 *
 */
public class Client {
	static SocketConnection socket;
	static Receiver receiver;
	static Sender sender;

	static int restart;
	public static boolean IS_START = false;

	private Client(String URL,int Port) {
		try {
//			socket = (SocketConnection) Connector.open("socket://localhost:34555",3, false);
			socket = (SocketConnection) Connector.open("socket://"+URL+":"+Port,3, false);
			receiver = new Receiver(socket.openInputStream());
			new Thread(receiver).start(); //启动拦截数据线程  
			sender = new Sender(socket.openOutputStream());
			new Thread(sender).start();//启动数据发送线程  
			IS_START = true;
			System.out.println("启动成功了");
		} catch (Exception e) {
			System.out.println("没启动成");
			IS_START = false;
			e.printStackTrace();
		}
	}

	public static void start(String URL,int Port) {
		new Client(URL,Port);
	}

	public static void stop() {
		try {
			if (socket != null){
				socket.close();
			}
			
			if (receiver != null){
				receiver.stop();
			}
			if (sender != null){
				sender.stop();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("stop error");
		}
		DataBuffer.setOffLine(true);
		DataBuffer.flushBuffer();
		IS_START = false;
		sender = null;
		receiver = null;
		socket = null;
	}

	/** 
	 * 发送指令 
	 */

	public static void send(byte[] data) {
		try {
//			System.out.println("sender = "+sender);
			if(sender!=null){
				sender.send(data);
				String ss = new String(data);
				if(!ss.equals("hi")){
					System.out.println("send = "+new String(data));
				}
			}
		} catch (Exception e) {
			System.out.println("send error");
			e.printStackTrace();
		}

	}

}