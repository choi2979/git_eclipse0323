package solo;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TimeServer extends JFrame implements Runnable {
	Socket socket = null;
	int port = 2000;
	ServerSocket serverSocket = null;
	List<TimeServerThread> globalList = null;
	JTextArea jta_log = new JTextArea();
	JScrollPane jsp_log = new JScrollPane(jta_log
				,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
				,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	TimeServerThread tst = null;
	public void initDisplay() {
		this.add("Center",jsp_log);
		this.setTitle("TimeServer 로그");
		this.setSize(400, 300);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		TimeServer ts = new TimeServer();
		ts.initDisplay();
		Thread th = new Thread(ts);
		th.start();
	}
	@Override
	public void run() {
		globalList = new Vector<>();
		try {
			serverSocket = new ServerSocket(port);
		} catch (Exception e) {
			// TODO: handle exception
		}
		jta_log.append("TimeServer started successfully..\n");
		while(true) {
			try {
				socket = serverSocket.accept();
				jta_log.append("New Client connected..."+socket.toString());
				tst = new TimeServerThread(this);
				tst.start();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public String setTimer() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);
		return (hour < 10 ? "0"+hour:""+hour)+":"+
				(min < 10 ? "0"+min:""+min)+":"+
				(sec < 10 ? "0"+sec:""+sec);
	}
	
}