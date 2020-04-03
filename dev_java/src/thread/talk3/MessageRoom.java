package thread.talk3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

//public class MessageRoom extends JPanel {
public class MessageRoom extends JPanel implements ActionListener{
	TalkClientVer2 	tc 				= null;
	//JPanel은 원래(디폴트) FlowLayout이다.
	//그러나 센터에 jta_display붙여야하고, 남쪽에 jp_first_south속지를 붙여야하니까 BorderLayout으로 변경하자
	JPanel 			jp_first 		= new JPanel();//<Div id="d_msg">
	StyledDocument 	sd_display 		= new DefaultStyledDocument(new StyleContext());
	JTextPane 		jta_display 	= new JTextPane(sd_display);
	JScrollPane		jsp_display		= new JScrollPane(jta_display
			,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
			,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	//남쪽에 들어갈 속지에 중앙에 jtf_msg를 배치하고 동쪽에 전송버튼을 배치하자.
	JPanel 			jp_first_south 	= new JPanel();//BorderLayout적용
	JTextField 		jtf_msg 		= new JTextField(20);
	JButton 		jbtn_send 		= new JButton("전송");
	
	JPanel 			jp_second		= new JPanel();
	JPanel			jp_second_south	= new JPanel();
	//남쪽에 들어갈 버튼 6개 추가하기
	JButton jbtn_whisper			= new JButton("1:1");
	JButton jbtn_change			= new JButton("대화명변경");
	JButton jbtn_fontColor			= new JButton("글자색");
	JButton jbtn_emoticon			= new JButton("이모티콘");
	JButton jbtn_blank			= new JButton("");
	JButton jbtn_exit			= new JButton("종료");
	String cols[] = {"대화명"};
	String data[][] = new String[0][1];
	
	DefaultTableModel dtm_nick = new DefaultTableModel(data,cols);
	
	JTable jtb_nick = new JTable(dtm_nick);
	JScrollPane		jsp_nick = new JScrollPane(jtb_nick
			,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
			,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	public MessageRoom(TalkClientVer2 tc) {
		this.tc = tc;
		initDisplay();
	}
	public MessageRoom() {
		// TODO Auto-generated constructor stub
	}


	public void initDisplay() {
		this.setLayout(new GridLayout(1,2));
		jp_second.setLayout(new BorderLayout());
		jp_second.add("Center",jsp_nick);
		jp_second_south.setLayout(new GridLayout(3,2));
		jp_second_south.add(jbtn_whisper);
		jp_second_south.add(jbtn_change);
		jp_second_south.add(jbtn_fontColor);
		jp_second_south.add(jbtn_emoticon);
		jp_second_south.add(jbtn_blank);
		jp_second_south.add(jbtn_exit);
		jp_second.add("South",jp_second_south);
		
		jp_first.setLayout(new BorderLayout());
		jp_first_south.setLayout(new BorderLayout());
		
		jp_first_south.add("Center",jtf_msg);
		jp_first_south.add("East",jbtn_send);
		jp_first.add("Center",jsp_display);
		jp_first.add("South",jp_first_south);
		this.add(jp_first);
		this.add(jp_second);
		this.setBackground(Color.YELLOW);
		this.setSize(500, 400);
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	public static void main(String args[]) {
		MessageRoom mr = new MessageRoom();
		mr.initDisplay();
	}
}
