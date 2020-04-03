package thread.talk3;

import java.awt.Color;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
//나는 oos와 ois를 어느 클래스에 생성해야 하는지 전혀 감이 없다.
//Login처리는 LoginForm에서 진행되므로 TalkClientVer2에서 생성하면 될 것이다.
public class TalkClientVer2 extends JFrame {
	JTabbedPane jtp = new JTabbedPane();
	WaitRoom wr = new WaitRoom(this);
	MessageRoom mr = new MessageRoom(this);
	SettingRoom sr = new SettingRoom(this);
	Socket mySocket = null;
	ObjectInputStream ois = null;
	ObjectOutputStream oos = null;
	final static String _IP = "192.168.0.119";
	final static int _PORT = 3000; //0~65575사이에 포트 사용가능
	//대화명을 담는 변수
	String nickName = null;
	LoginForm loginForm = null;
	public TalkClientVer2() {
	}
	public TalkClientVer2(LoginForm loginForm) {
		this.loginForm = loginForm;
		nickName = loginForm.nickName;//로그인 화면에서 결정된 대화명으로 동기화
		initDisplay();
		//화면 처리 후 서버 소켓 접속하기
		connect_process();
	}
	private void connect_process() {
		this.setTitle(nickName+"님의 대화창");
		try {
			mySocket = new Socket(_IP,_PORT);
			oos = new ObjectOutputStream(mySocket.getOutputStream());
			ois = new ObjectInputStream(mySocket.getInputStream());
			oos.writeObject(Protocol.WAIT+"#"+nickName+"#"+"대기");
			//말하고 듣기
			//내가 한 말도 서버를 경유하여 듣는다.(run) - 꼭 기억할것.
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
		//mr.setBackground(Color.CYAN);
		this.getContentPane().setBackground(new Color(158,217,24));
		jtp.setBounds(5, 4, 620, 530);
		this.getContentPane().add(jtp);
		this.setSize(650, 580);
		this.setVisible(true);
		jtp.setSelectedIndex(0);
	}
	public static void main(String[] args) {
		TalkClientVer2 tc = new TalkClientVer2(new LoginForm());
		
	}

}
