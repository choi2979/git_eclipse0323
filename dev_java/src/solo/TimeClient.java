package solo;

import java.awt.Font;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class TimeClient extends Thread {
	JFrame jf = new JFrame();
	JLabel jlb_time = null;
	JLabel jlb_time2 = new JLabel("현재시간",JLabel.CENTER);
	
	public TimeClient() {
		jf.add("North",jlb_time);
		jf.setSize(500, 400);
		jf.setVisible(true);
	}
	public TimeClient(JLabel jlb_time) {
		this.jlb_time = jlb_time;
	}
	public void run() {
		String time = null;
		Socket socket = null;
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		try {
			socket = new Socket("192.168.0.119",2000);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			while(true) {
				time = (String)ois.readObject();
				Font f = new Font("sans serif",Font.BOLD,30);
				jlb_time.setFont(f);
				jlb_time.setText(time);
				try {
					sleep(1000);
				} catch (Exception e) {
					System.out.println("앗 ~~...");
				}
			}
		} catch (Exception e) {
			System.out.println("타입서버에 접속할 수 없습니다.");
		}
	}
	
	
	  public static void main(String[] args) { 
		  TimeClient tc = new TimeClient();
		  tc.start(); 
	  }
	 
	
}
