package solo;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.text.SimpleAttributeSet;

public class EmoticonView extends JDialog implements ActionListener{
	JButton emot1 = new JButton();
	JButton emot2 = new JButton();
	JButton emot3 = new JButton();
	JButton emot4 = new JButton();
	JButton emot5 = new JButton();
	JButton emot6 = new JButton();
	String imgFile[] = {"lion11.png","lion22.png","lion33.png","lion44.png","lion55.png","lion66.png"};
	JButton emots[] = {emot1, emot2, emot3, emot4, emot5, emot6};
	ImageIcon imgs[] = new ImageIcon[imgFile.length];
	String path = "src//emoticon//";
	String imgChoice = "default";
	TalkClient tc = null;
	public EmoticonView(TalkClient tc) {
		this.tc = tc;
		emot1.addActionListener(this);
		emot2.addActionListener(this);
		emot3.addActionListener(this);
		emot4.addActionListener(this);
		emot5.addActionListener(this);
		emot6.addActionListener(this);
	}
	public EmoticonView() {}
	public void initDisplay() {
		this.setLayout(new GridLayout(2,3,2,2));
		
		for(int i=0;i<emots.length;i++) {
			imgs[i] = new ImageIcon(path+imgFile[i]);
			emots[i].setIcon(imgs[i]);
			emots[i].setBorderPainted(false);//
			emots[i].setFocusPainted(false);
			emots[i].setContentAreaFilled(false);
			this.add(emots[i]);
		}
		this.setTitle("이모티콘");
		this.pack();
		this.setVisible(true);
	}
	public static void main(String[] args) {
		EmoticonView ev =new EmoticonView(null);
		ev.initDisplay();

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj== emot1) {
			imgChoice = "lion11.png";
			tc.message_process(null,imgChoice);
			this.setVisible(false);
		}
		if(obj== emot2) {
			imgChoice = "lion22.png";
			tc.message_process(null,imgChoice);
			this.setVisible(false);
		}
		if(obj== emot3) {
			imgChoice = "lion33.png";
			tc.message_process(null,imgChoice);
			this.setVisible(false);
		}
		if(obj== emot4) {
			imgChoice = "lion44.png";
			tc.message_process(null,imgChoice);
			this.setVisible(false);
		}
		if(obj== emot5) {
			imgChoice = "lion55.png";
			tc.message_process(null,imgChoice);
			this.setVisible(false);
		}
		if(obj== emot6) {
			imgChoice = "lion66.png";
			tc.message_process(null,imgChoice);
			this.setVisible(false);
		}
	}

}
