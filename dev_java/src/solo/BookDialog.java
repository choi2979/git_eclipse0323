package solo;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import design.book.BookVO;

public class BookDialog extends JDialog implements ActionListener {
	JLabel jlb_title = new JLabel("도서명");
	JTextField jtf_title = new JTextField(20);
	JLabel jlb_author = new JLabel("저자");
	JTextField jtf_author = new JTextField(20);
	JLabel jlb_publish = new JLabel("출판사");
	JTextField jtf_publish = new JTextField(20);
	JLabel jlb_info = new JLabel("도서소개");
	JTextArea jta_info = new JTextArea(8,25);
	JScrollPane jsp_info = new JScrollPane(jta_info);
	JPanel jp_center = new JPanel();
	JScrollPane jsp = new JScrollPane(jp_center);
	JLabel jlb_img = new JLabel("이미지 없음");
	JButton jbtn_file = new JButton("파일 찾기");
	JTextField jtf_file = new JTextField(30);
	JPanel jp_south = new JPanel();
	JButton jbtn_save = new JButton("저장");
	JButton jbtn_exit = new JButton("닫기");
	BookApp ba = null;
	boolean isView = false;
	String title = null;
	BookVO rbVO = null;
	ImageIcon icon = null;
	String imgPath = "src\\design\\book\\";
	
	public BookDialog() {
		jbtn_save.addActionListener(this);
	}
	
	
	public void set(String title, boolean isView, boolean editable, BookVO rbVO, BookApp ba) {
		this.ba = ba;
		this.rbVO = rbVO;
		setValue(rbVO);
		setEditable(editable);
		this.setTitle(title);
		initDisplay();
		this.setVisible(isView);
		
	}
	
	public void setEditable(boolean editable) {
		jtf_title.setEditable(editable);
		jtf_author.setEditable(editable);
		jtf_publish.setEditable(editable);
		jta_info.setEditable(editable);
		
	}

	public void setValue(BookVO rbVO) {
		if(rbVO == null) {
			setB_title("");
			setB_author("");
			setB_publish("");
			setB_info("");
			setB_img("");
		}
		else {
			setB_title(rbVO.getB_title());
			setB_author(rbVO.getB_author());
			setB_publish(rbVO.getB_publish());
			setB_info(rbVO.getB_info());
			setB_img(rbVO.getB_img());
		}
		
	}


	public void initDisplay() {
		jp_center.setLayout(null);
		jta_info.setLineWrap(true);
		jlb_title.setBounds(20, 20, 100, 20);
		jtf_title.setBounds(120, 20, 200, 20);
		jlb_author.setBounds(20, 45, 100, 20);
		jtf_author.setBounds(120, 45, 200, 20);
		jlb_publish.setBounds(20, 70, 100, 20);
		jtf_publish.setBounds(120, 70, 200, 20);
		jlb_info.setBounds(20, 95, 100, 20);
		jsp_info.setBounds(120, 95, 300, 160);
		jbtn_file.setBounds(20, 260, 100, 20);
		jtf_file.setBounds(120, 260, 350, 20);
		jlb_img.setBorder(BorderFactory.createEtchedBorder());
		jlb_img.setBounds(120, 285, 300, 360);
		jp_center.add(jlb_title);
		jp_center.add(jtf_title);
		jp_center.add(jlb_author);
		jp_center.add(jtf_author);
		jp_center.add(jlb_publish);
		jp_center.add(jtf_publish);
		jp_center.add(jlb_info);
		jp_center.add(jsp_info);
		jp_center.add(jbtn_file);
		jp_center.add(jtf_file);
		jp_center.add(jlb_img);
		jp_south.add(jbtn_save);
		jp_south.add(jbtn_exit);
		this.add("Center",jsp);
		this.add("South",jp_south);
		this.setSize(500, 720);
	}
	
	public String getB_title() {	return jtf_title.getText();}
	public void setB_title(String title) { jtf_title.setText(title);}
	public String getB_author() {	return jtf_author.getText();}
	public void setB_author(String author) { jtf_author.setText(author);}
	public String getB_publish() {	return jtf_publish.getText();}
	public void setB_publish(String publish) { jtf_publish.setText(publish);}
	public String getB_info() {	return jta_info.getText();}
	public void setB_info(String info) { jta_info.setText(info);}
	public void setB_img(String img) {
		icon = new ImageIcon(imgPath+img);
		
		Image originImg = icon.getImage();
		
		Image changeImg = originImg.getScaledInstance(300, 380, Image.SCALE_SMOOTH);
		
		ImageIcon cicon = new ImageIcon(changeImg);
		
		jlb_img.setIcon(cicon);
		
	}
	
	public static void main(String[] args) {
		BookDialog bd = new BookDialog();
		bd.set("입력",true,true,new BookVO(),null);
		bd.initDisplay();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		if("저장".equals(command)) {
			int result = 0;
			
			if(rbVO!=null) {
				BookVO pbVO = new BookVO();
				//pbVO.setCommand("update");
				pbVO.setB_no(rbVO.getB_no());
				pbVO.setB_title(getB_title());
				pbVO.setB_author(getB_author());
				pbVO.setB_publish(getB_publish());
				pbVO.setB_info(getB_info());
				result=ba.bDao.bookUpdate(pbVO);
			}
			else {
				BookVO pbVO = new BookVO();
				pbVO.setB_title(getB_title());
				pbVO.setB_author(getB_author());
				pbVO.setB_publish(getB_publish());
				pbVO.setB_info(getB_info());
				result=ba.bDao.bookInsert(pbVO);
			}
			ba.refreshData();
			this.dispose();
		}
		else if("닫기".equals(command)) {
			this.dispose();
		}
		
	}
	
}
