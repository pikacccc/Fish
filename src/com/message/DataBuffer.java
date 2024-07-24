package com.message;

import java.util.Vector;

/**
 * 
 * @author wangjun04
 *
 */
public class DataBuffer{
	
	/**
	 * the receiving data
	 */
//	private static byte[] rBuf;
	
	/**
	 * 断线处理标志
	 */
	private static boolean offLine = false;
	
	private static Vector dBuf = new Vector();
	
	public static synchronized void setOffLine(boolean isOffLine)
	{
		offLine = isOffLine;
		System.out.println("set offLine"+offLine);
	}
	
	public static synchronized boolean isOffLine()
	{
		return offLine;
	}

	public static synchronized void flushBuffer(){
		if(dBuf.size()>0){
			dBuf.removeAllElements();
		}
	}
	
	/**
	 * set receiving data
	 * @param buffer
	 */
	protected static synchronized void setReceiveBufBytes(byte[] buffer) {
		byte[] v = new byte[buffer.length];
		System.arraycopy(buffer, 0, v, 0, v.length);
		dBuf.addElement(v);
	} 
	
	public static synchronized byte[] getReceiveBufBytes() {
		if(dBuf.size() > 0)
		{
//			System.out.println("dBuf.size() = "+dBuf.size());
			byte[] data = (byte [])dBuf.elementAt(0);
			dBuf.removeElementAt(0);
			return data;
		}else{
			return null;
		}
	}

}
