package thread.file;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class FileClient extends JFrame {
	Socket 			socket 	= null;
	JTextArea 		jta_log = new JTextArea(10,30);
	JScrollPane 	jsp_log = new JScrollPane(jta_log
			,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
			,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	public void initDisplay() {
		this.setTitle("★★★★★클라이언트★★★★★");
		this.add("Center",jsp_log);
		this.setSize(500, 400);
		this.setVisible(true);
	}
	
	public void init() {
		try {//네트워크에서 하는건 예외처리
			//서버측의 ip주소 작성하기
			socket = new Socket("192.168.0.119", 3000);
			FileClientThread fct = new FileClientThread(this);
			fct.start();
		} catch (Exception e) {
			//예외가 발생했을 때 직접적인 원인이되는 클래스명 출력하기
			System.out.println(e.toString());
		}
	}
	
	public static void main(String[] args) {
		FileClient fc = new FileClient();
		fc.initDisplay();
		fc.init();

	}
	

}
