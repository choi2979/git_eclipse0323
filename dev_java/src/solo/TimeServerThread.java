package solo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class TimeServerThread extends Thread {
	TimeServer ts = null;
	ObjectOutputStream oos= null;
	ObjectInputStream ois = null;
	String time = "";
	public TimeServerThread(TimeServer ts) {
		this.ts = ts;
		try {
			oos = new ObjectOutputStream(ts.socket.getOutputStream());
			ois = new ObjectInputStream(ts.socket.getInputStream());
			
			time = ts.setTimer();
			oos.writeObject(time);
			
			for(TimeServerThread tst:ts.globalList) {
				this.send(time);
			}
			ts.globalList.add(this);
			this.broadCasting(time);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	public void broadCasting(String msg) {
		ts.jta_log.append("현재 인원수:"+ts.globalList.size());
		synchronized (this) {
			for(TimeServerThread tst:ts.globalList) {
				tst.send(msg);
			}
		}
		
	}
	public void send(String msg) {
		try {
			oos.writeObject(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		while(true) {
			try {
				time = ts.setTimer();
				oos.writeObject(time);
				sleep(1000);
			} catch (IOException ie) {
				System.out.println(ie.toString());
			} catch (InterruptedException ie) {
				System.out.println("다른 스레가 새치기 했음");
			}
		}
	}
	
}
