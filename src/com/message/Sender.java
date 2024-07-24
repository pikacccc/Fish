package com.message;


import java.io.IOException;
import java.io.OutputStream;

//import land.Globe;




/**
 * 
 * @author wangjun04
 *
 */
public class Sender implements Runnable {

	public OutputStream os;

	private byte[] data;

	private boolean stop = false;

	Sender(OutputStream os) {
		this.os = os;

	}

	/**
	 * 发送数据
	 * @param data
	 */
	public synchronized void send(byte[] data) {
		this.data = data;
		notify(); // 执行运行
	}

	/**
	 * 执行，监听客户发送指令，如果指令不为null则工作， 否则暂停工作直到有客户发送指令为止才工作，
	 */
	public synchronized void run() {
		try {
			runImpl();
		} catch (Exception e) {
			e.printStackTrace();
			//Globe.setOffLineStr = "写入"+e.toString();
			DataBuffer.setOffLine(true);
		}
	}

	private void runImpl() throws IOException {
		while (true) {
			if (stop)
				break;

			// If no client to deal, wait until one connects
			if (data == null) {
				try {
					wait();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			wirte();
		}
	}
	
	private byte[] getBytes(byte[] data) {
		if (data == null)
			return new byte[4];
		int length = data.length;
		byte[] buf = new byte[length + 4];
		buf[3] = (byte) ((length >> 24) & 0xff);
		buf[2] = (byte) ((length >> 16) & 0xff);
		buf[1] = (byte) ((length >> 8) & 0xff);
		buf[0] = (byte) ((length >> 0) & 0xff);
		System.arraycopy(data, 0, buf, 4, length);
		return buf;
	}

	/**
	 * 
	 * @throws IOException
	 * 
	 */
	private void wirte() throws IOException {
		/*
		if(Globe.isApendDataLenght){
			os.write(getBytes(data));
		}else{
		*/
			os.write(data);
		//}
		
		
		os.flush();
		data = null; // 指令为空,等待下一个指
	}

	private int getInt(byte[] data) {
		if (data == null)
			return -1;

		int num = 0;

		for (int i = 0; i < data.length; i++) {
			num += ((data[i] & 0xff) << (8 * i));
		}

		return num;
	}
	

	
	public synchronized void stop() {
		stop = true;
		if (os != null) {
			try {
				os.close();
				os = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		notify();

	}

}