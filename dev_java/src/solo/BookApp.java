package solo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import design.book.BookVO;

public class BookApp extends JFrame implements ActionListener {
	String imgpath = "src\\design\\book\\";
	URL bookURL = getClass().getResource("good.jpg");
	ImageIcon ii = new ImageIcon(bookURL);
	JMenuBar jmb_book = new JMenuBar();
	JMenu jm_file = new JMenu("File");
	JMenuItem jmi_exit= new JMenuItem("Exit");
	JMenuItem jmi_db = new JMenuItem("DB 연결");
	JMenuItem jmi_of = new JMenuItem("Open File");
	JMenu jm_edit = new JMenu("Edit");
	JMenuItem jmi_all = new JMenuItem("전체조회");
	JMenuItem jmi_sel = new JMenuItem("상세조회",new ImageIcon(imgpath+"detail.gif"));
	JMenuItem jmi_ins = new JMenuItem("입력",new ImageIcon(imgpath+"new.gif"));
	JMenuItem jmi_upd = new JMenuItem("수정",new ImageIcon(imgpath+"update.gif"));
	JMenuItem jmi_del = new JMenuItem("삭제",new ImageIcon(imgpath+"delete.gif"));
	static BookApp ba = null;
	JSeparator js_file = new JSeparator();
	JToolBar jtbar = new JToolBar();
	JButton jbtn_db = new JButton("DB 연결");
	JButton jbtn_all = new JButton("전체조회");
	JButton jbtn_sel = new JButton();
	JButton jbtn_ins = new JButton();
	JButton jbtn_upd = new JButton();
	JButton jbtn_del = new JButton();
	String cols[] = {"도서번호","도서명","저자","출판사"};
	String data[][] = new String[0][4];
	DefaultTableModel dtm_book = new DefaultTableModel(data,cols);
	JTable jtb_book = new JTable(dtm_book);
	JScrollPane jsp_book = new JScrollPane(jtb_book);
	BookController bCtrl = new BookController(this);
	BookDao bDao = new BookDao();
	BookDialog bd = new BookDialog();
	JLabel jlb_time = new JLabel("현재시간",JLabel.CENTER);
	TimeClient tc = null;
	public static void main(String[] args) {
//		TimeServer ts = new TimeServer();
//		ts.initDisplay();
//		Thread th = new Thread(ts);
//		th.start();
		ba = new BookApp();
		ba.initDisplay();
	}
	public void initDisplay() {
		tc = new TimeClient(jlb_time);
		tc.start();
		jbtn_del.addActionListener(this);
		jbtn_upd.addActionListener(this);
		jbtn_sel.addActionListener(this);
		jbtn_all.addActionListener(this);
		jbtn_ins.addActionListener(this);
		this.add("Center", jsp_book);
		jtbar.add(jbtn_db);
		jtbar.add(jbtn_all);
		jtbar.add(jbtn_sel);
		jtbar.add(jbtn_ins);
		jtbar.add(jbtn_upd);
		jtbar.add(jbtn_del);
		this.add("North",jtbar);
		jm_edit.add(jmi_all);
		jm_edit.add(jmi_sel);
		jm_edit.add(jmi_ins);
		jm_edit.add(jmi_upd);
		jm_edit.add(jmi_del);
		jm_file.add(jmi_db);
		jm_file.add(jmi_of);
		jm_file.add(js_file);
		jm_file.add(jmi_exit);
		jmb_book.add(jm_file);
		jmb_book.add(jm_edit);
		jbtn_sel.setIcon(new ImageIcon(imgpath+"detail.gif"));
		jbtn_ins.setIcon(new ImageIcon(imgpath+"new.gif"));
		jbtn_upd.setIcon(new ImageIcon(imgpath+"update.gif"));
		jbtn_del.setIcon(new ImageIcon(imgpath+"delete.gif"));
		jbtn_sel.setToolTipText(jmi_sel.getText());
		jbtn_ins.setToolTipText(jmi_ins.getText());
		jbtn_upd.setToolTipText(jmi_upd.getText());
		jbtn_del.setToolTipText(jmi_del.getText());
		this.add("South",jlb_time);
		this.setJMenuBar(jmb_book);
		this.setTitle("도서관리시스템");
		this.setSize(650, 400);
		this.setIconImage(ii.getImage());
		this.setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		if(obj == jbtn_del) {
			int indexs[] = jtb_book.getSelectedRows();
			if(indexs.length==0) {
				JOptionPane.showMessageDialog(this, "삭제할 로우를 선택하세요.");
				return;
			}
			else {
				List<Integer> bnos = new ArrayList<Integer>();
				for(int i = 0;i<dtm_book.getRowCount();i++) {
					if(jtb_book.isRowSelected(i)) {
						int b_no = Integer.parseInt(dtm_book.getValueAt(i, 0).toString());
						bnos.add(b_no);
					}
				}
				BookVO pbVO = new BookVO();
				pbVO.setCommand("delete");
				pbVO.setBnos(bnos);
				int result = 0;
				BookVO rbVO = new BookVO();
				rbVO = bCtrl.send(pbVO);
				result = rbVO.getResult();
				if(result > 0) {
					JOptionPane.showMessageDialog(this, "삭제처리되었습니다.");
					refreshData();
				} else {
					JOptionPane.showMessageDialog(this, "실패");
					refreshData();
				}
			}
		}
		else if(obj == jbtn_upd) {
			BookVO rbVO = null;
			int index = 0;
			
			index = jtb_book.getSelectedRow();
			if(index >=0) {
				BookVO pbVO = new BookVO();
				pbVO.setCommand("detail");
				int b_no = Integer.parseInt(dtm_book.getValueAt(index, 0).toString());
				pbVO.setB_no(b_no);
				rbVO = bCtrl.send(pbVO);
			}
			else {
				JOptionPane.showMessageDialog(this, "수정할 데이터를 선택하세요.");
				return;
			}
			bd.set("수정", true, true, rbVO, ba);
			
			
		}
		else if(obj == jbtn_ins) {
			BookVO bVO = null;
			bd.set("입력", true, true, bVO, ba);
		}
		else if(jbtn_sel == obj) {
			int indexs = jtb_book.getSelectedRow();
			if(indexs==0) {
				JOptionPane.showMessageDialog(this, "상세조회할 로우를 고르시오.");
				return;
			}
			else {
				int b_no = Integer.parseInt(dtm_book.getValueAt(indexs, 0).toString());
				BookVO pbVO = new BookVO();
				pbVO.setCommand("detail");
				pbVO.setB_no(b_no);
				BookVO rbVO = bCtrl.send(pbVO);
				bd.set("상세조회", true, false, rbVO, null);
			}
		}
		else if(jbtn_all == obj) {
			refreshData();
		}
		
	}
	public void refreshData() {
		List<BookVO> bookList = null;
		BookVO pbVO = new BookVO();
		pbVO.setCommand("all");
		bookList = bCtrl.sendALL(pbVO);
		
		while(dtm_book.getRowCount()>0) {
			dtm_book.removeRow(0);
		}
		
		for(int i=0; i<bookList.size();i++) {
			BookVO bVO = bookList.get(i);
			Vector<Object> v = new Vector<Object>();
			v.add(bVO.getB_no());
			v.add(bVO.getB_title());
			v.add(bVO.getB_author());
			v.add(bVO.getB_publish());
			dtm_book.addRow(v);
		}
		
	}
}
