package com.message;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

//import land.Globe;






/**
 * 
 * @author wangjun04
 *
 */
public class Receiver implements Runnable {
	
	private InputStream is;
	private boolean stop = false;
	private byte[] content = null;

	protected Receiver(InputStream is) {
		this.is = is;
	}

	/** 
	 * 程序启动后不停的获取数据,并根据数据的类型做相应的工作 
	 */
	public synchronized void run() {
		try {
			runImpl();
		} catch (Exception e) {
			//Globe.setOffLineStr = "读取"+e.toString();
			e.printStackTrace();
			DataBuffer.setOffLine(true);
		}
	}

	/**
	 * run the thread get the message
	 * @throws IOException
	 */
	private void runImpl() throws IOException {
		while (!stop) {
//			while (true) {
				receive();

				if (content == null)
					continue;

				executeReceive();

//			}
		}
	}

	//获取数据后的具体工作  
	private void executeReceive() {
		// TODO Auto-generated method stub  
		DataBuffer.setReceiveBufBytes(content);
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
	
	
	/** 
	 * 读取数据 
	 *  
	 * @throws IOException 
	 */
	private void receive() throws IOException {
		content = null;
		int c = 0;
		ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
		/*
		if(Globe.isApendDataLenght){
			byte[] l = new byte[4];
			if(is == null){
				return;
			}
			is.read(l, 0, 4);
			int length = getInt(l);
			if(length <=0){
				return;
			}
			for (int j = 0; j < length; j++) {
				c = is.read();
				bytearrayoutputstream.write(c);
			}
			content = bytearrayoutputstream.toByteArray();
		}else{
		*/	
			while((c = is.read())!=-1){
				bytearrayoutputstream.write(c);
			}
			content = bytearrayoutputstream.toByteArray();

		//}
	}

	public void stop() {
		stop = true;
		if (is != null) {
			try {
				is.close();
				is = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}