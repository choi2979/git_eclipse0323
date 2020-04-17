package solo;

import java.awt.BorderLayout;
import java.awt.Color;
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

public class CreateView extends JFrame implements ActionListener {
	JPanel jp_first = new JPanel();
	JPanel jp_second = new JPanel();
	JPanel jp_first_south = new JPanel();
	JPanel jp_second_east = new JPanel();
	JButton jbtn_emo = new JButton("이모티콘");
	JButton jbtn_wel = new JButton("초대");
	JButton jbtn_change = new JButton("대화명변경");
	JButton jbtn_font = new JButton("글자색");
	JButton jbtn_exit = new JButton("나가기");
	String cols[]	= {"대화명"};
	String data[][] = new String[0][1];
	DefaultTableModel dtm = new DefaultTableModel(data,cols);
	JTable jtb = new JTable(dtm);
	JScrollPane jsp = new JScrollPane(jtb);
	
	StyledDocument sd_display = new DefaultStyledDocument(new StyleContext());
	JTextPane jtp_display = new JTextPane(sd_display);
	JScrollPane jsp_display = new JScrollPane(jtp_display);
	JButton jbtn_send = new JButton("전송");
	JTextField jtf_msg = new JTextField(20);
	TalkClient tc = null;
	String fontColor = "0";
	public void initDisplay() {
		this.setLayout(new GridLayout(1,2));
		jp_second_east.setLayout(new GridLayout(5,1));
		jp_second.setLayout(new BorderLayout());
		jp_second.add(jsp);
		jp_second_east.add(jbtn_emo);
		jp_second_east.add(jbtn_wel);
		jp_second_east.add(jbtn_change);
		jp_second_east.add(jbtn_font);
		jp_second_east.add(jbtn_exit);
		jp_second.add("East",jp_second_east);
		jp_first.setLayout(new BorderLayout());
		jp_first_south.setLayout(new BorderLayout());
		jp_first_south.add("Center",jtf_msg);
		jp_first_south.add("East",jbtn_send);
		Font font = new Font("돋움",Font.BOLD,25);
		jp_first.add("Center",jsp_display);
		jp_first.add("South",jp_first_south);
		this.add(jp_first);
		this.add(jp_second);
		this.setSize(800, 550);
		this.setVisible(true);
	}
	public CreateView(TalkClient tc) {
		this.tc = tc;
		jtf_msg.addActionListener(this);
		jbtn_emo.addActionListener(this);
		jbtn_wel.addActionListener(this);
		jbtn_exit.addActionListener(this);
		jbtn_change.addActionListener(this);
		jbtn_font.addActionListener(this);
	}
	public static void main(String[] args) {
		CreateView cv = new CreateView(null);
		cv.initDisplay();

	}
	public void message_process(String msg, String imgChoice) {
		if(imgChoice != null) {
			try {
				msg= "이모티콘";
				tc.oos.writeObject(201
						+"#"+tc.loginForm.nickName
						+"#"+msg
						+"#"+fontColor
						+"#"+tc.emov.imgChoice);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		else {
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
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		String msg = jtf_msg.getText();
		if(jbtn_emo.equals(obj)) {
			tc.emov.initDisplay();
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
			jdl_color.add(jcc);
			jdl_color.setVisible(true);
		}
		else if(jbtn_wel == obj) {
			
		}
		else if(jtf_msg==obj) {
			message_process(msg, null);
		}
		else if(jbtn_exit==obj) {
			try {
				tc.oos.writeObject(500+"#"+tc.loginForm.nickName);
				System.exit(0);
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		else if(jbtn_change==obj) {
			try {
				String afterName = JOptionPane.showInputDialog("새 닉네임을 입력하세요.");
				if(afterName == null || afterName.trim().length()<1) {
					JOptionPane.showMessageDialog(this, "변경할 대화명을 입력하세요.","INFO", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				try {
					tc.oos.writeObject(202
							+"#"+tc.loginForm.nickName
							+"#"+afterName
							+"#"+tc.loginForm.nickName+"의 대화명이"+afterName+"으로 변경되었습니다.");
				} catch (Exception e2) {
					// TODO: handle exception
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
}
