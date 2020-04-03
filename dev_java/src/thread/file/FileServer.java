package thread.file;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import thread.talk.TalkServerThread;

public class FileServer extends JFrame implements Runnable{
	ServerSocket 	server 	= null;//경합이 벌어짐
	Socket 			socket 	= null;//그 순간에는 하나
	List<FileServerThread> globalList = null;
	Map<String, FileServerThread> map = new HashMap<>();
	JTextArea 		jta_log = new JTextArea(10,30);
	JScrollPane 	jsp_log = new JScrollPane(jta_log
			,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
			,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	public void initDisplay() {
		this.setTitle("★★★★★서버 로그★★★★★");
		this.add("Center",jsp_log);
		this.setSize(500, 400);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		FileServer fs = new FileServer();
		fs.initDisplay();
		Thread th = new Thread(fs);
		th.start();
		

	}
	@Override
	public void run() {
		globalList = new Vector<FileServerThread>();
		boolean isStop = false;
		try {
			server = new ServerSocket(3000);
			jta_log.append("Server Ready............\n");
			while(!isStop) {
				socket = server.accept();
				jta_log.append("Client info:"+socket+"\n");
				FileServerThread fst = new FileServerThread(this);
				fst.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
