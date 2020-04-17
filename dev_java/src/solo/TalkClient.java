package solo;

import java.awt.Color;
import java.awt.Component;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class TalkClient extends JFrame{
	////////////////통신과 관련한 전역변수 추가 시작/////////////////////////
	JTabbedPane jtp = new JTabbedPane();
	WaitRoom wr = new WaitRoom(this);
	MessageRoom mr = new MessageRoom(this);
	SettingRoom sr = new SettingRoom(this);
	Socket mySocket = null;
	ObjectInputStream ois = null;
	ObjectOutputStream oos = null;
	final static String _IP = "192.168.0.119";
	final static int _PORT = 3000;
	
	String nickName = null;
	LoginForm loginForm = null;
	public TalkClient()	{
		
	}
	public TalkClient(LoginForm lf) {
		this.loginForm = lf;
		nickName = lf.nickName;
		initDisplay();
		connect_process();
	}
	public void connect_process() {
		this.setTitle(nickName+"님의 대화창");
		try {
			mySocket = new Socket(_IP,_PORT);
			oos = new ObjectOutputStream(mySocket.getOutputStream());
			ois = new ObjectInputStream(mySocket.getInputStream());
			oos.writeObject(Protocol.WAIT+"#"+nickName+"#"+"대기");
			
			TalkClientThread tct = new TalkClientThread(this);
			tct.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public void initDisplay() {
		this.getContentPane().setLayout(null);
		jtp.addTab("대기실", wr);
		jtp.addTab("단톡방", mr);
		jtp.addTab("설정", sr);
		this.getContentPane().setBackground(new Color(158,217,24));
		jtp.setBounds(5,4,620,530);
		this.getContentPane().add(jtp);
		this.setSize(650, 580);
		this.setVisible(true);
		jtp.setSelectedIndex(0);
		
	}
	public static void main(String[] args) {
		TalkClient tc = new TalkClient(new LoginForm());
	}
	
}
