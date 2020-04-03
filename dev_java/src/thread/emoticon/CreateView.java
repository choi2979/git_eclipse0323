package thread.emoticon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class CreateView extends JFrame implements ActionListener{
	JPanel jp_second		= new JPanel();
	JPanel jp_second_south	= new JPanel();
	JButton jbtn_emot		= new JButton("이모티콘");
	JButton jbtn_wel	= new JButton("초대");
	JButton jbtn_change		= new JButton("대화명변경");
	JButton jbtn_font		= new JButton("글자색");
	JButton jbtn_exit		= new JButton("나가기");
	String cols[] 			= {"대화명"};
	String data[][] 		= new String[0][1];
	DefaultTableModel dtm 	= new DefaultTableModel(data,cols);
	JTable jtb 				= new JTable(dtm);
	JScrollPane jsp 		= new JScrollPane(jtb);
	JPanel jp_first 		= new JPanel();
	JPanel jp_first_south 	= new JPanel();
	JTextField jtf_msg 		= new JTextField(20);//south속지 center
	JButton jbtn_send 		= new JButton("전송");//south속지 east
	StyledDocument sd_display = new DefaultStyledDocument(new StyleContext());
	JTextPane jtp_display 	= new JTextPane(sd_display);
	JScrollPane jsp_display = new JScrollPane(jtp_display
			,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
			,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	String fontColor = "0";
	TalkClient tc = null;
	public CreateView(TalkClient tc) {
		this.tc = tc;
		jtf_msg.addActionListener(this);
		jbtn_emot.addActionListener(this);
		jbtn_wel.addActionListener(this);
		jbtn_exit.addActionListener(this);
		jbtn_change.addActionListener(this);
		jbtn_font.addActionListener(this);
	}
	public void initDisplay() {
		this.setLayout(new GridLayout(1,2));
		jp_second.setLayout(new BorderLayout());
		jp_second.add("Center",jsp);
		jp_second_south.setLayout(new GridLayout(5,1));
		jp_second_south.add(jbtn_emot);
		jp_second_south.add(jbtn_wel);
		jp_second_south.add(jbtn_change);
		jp_second_south.add(jbtn_font);
		jp_second_south.add(jbtn_exit);
		jp_second.add("East",jp_second_south);
		jp_first.setLayout(new BorderLayout());
		jp_first_south.setLayout(new BorderLayout());
		jp_first_south.add("Center",jtf_msg);
		jp_first_south.add("East",jbtn_send);
		Font font = new Font("돋움",Font.BOLD,25);
		//jsp_display = new JScrollPane(jta_display);
		jp_first.add("Center",jsp_display);
		jp_first.add("South",jp_first_south);
		this.add(jp_first);
		this.add(jp_second);
		this.setSize(800, 550);
		this.setVisible(true);
		
	}
	public static void main(String[] args) {
		CreateView cv = new CreateView(null);
		cv.initDisplay();

	}
	public void message_process(String msg, String imgChoice) {
		if(imgChoice != null) {//이모티콘을 선택했다.
			try {
				msg = "이모티콘";
				tc.oos.writeObject(201
						+"#"+tc.loginForm.nickName
						+"#"+msg
						+"#"+fontColor
						+"#"+tc.emov.imgChoice);
				jtf_msg.setText("");
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		else {//일반 메시지를 입력했다.
			try {
				tc.oos.writeObject(201
						+"#"+tc.loginForm.nickName
						+"#"+msg
						+"#"+fontColor
						+"#"+"default");
				jtf_msg.setText("");
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		String msg = jtf_msg.getText();
		if(jbtn_emot.equals(obj)) {
			tc.emov.initDispaly();
		}
		else if(jbtn_font == obj) {
			JDialog jdl_color = new JDialog();
			jdl_color.setSize(600, 500);
			JColorChooser jcc = new JColorChooser();
			ColorSelectionModel csm = jcc.getSelectionModel();
			ChangeListener cl = new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					Color color = jcc.getColor();
					fontColor = String.valueOf(color.getRGB());
					
				}
			};
			csm.addChangeListener(cl);
			//jta_display.setForeground(fontColor);
			jdl_color.add(jcc);
			jdl_color.setVisible(true);
			
		}
		else if(jbtn_wel == obj) {
			
		}/////////////////////////////////////if
		else if(jtf_msg==obj) {
			//JOptionPane.showMessageDialog(this, "선택한 이미지:"+emov.imgChoice);
			message_process(msg, null);
		}
		else if(jbtn_exit==obj) {
			try {
				tc.oos.writeObject(500+"#"+tc.loginForm.nickName);
				System.exit(0);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		else if(jbtn_change==obj) {
			try {
				String afterName = JOptionPane.showInputDialog("새 닉네임을 입력하세요.");
				if(afterName == null || afterName.trim().length()<1) {
					JOptionPane.showMessageDialog(this,
							"변경할 대화명을 입력하세요",
							"INFO", JOptionPane.INFORMATION_MESSAGE);
				return;	
				}
				try {
					tc.oos.writeObject(202
							+"#"+tc.loginForm.nickName
							+"#"+afterName
							+"#"+tc.loginForm.nickName+"의 대화명이"+afterName+"으로 변경되었습니다.");
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}

}
