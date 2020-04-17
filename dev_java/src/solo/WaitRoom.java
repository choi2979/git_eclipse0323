package solo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class WaitRoom extends JPanel implements ActionListener, MouseListener {
	String roomTitle = null;
	TalkClient tc = null;
	JPanel jp_first = new JPanel();
	String cols[] = {"대화명","위치"};
	String data[][]	= new String[0][2];
	DefaultTableModel dtm_wait = new DefaultTableModel(data, cols);
	JTable jtb_wait = new JTable(dtm_wait);
	JScrollPane jsp_wait = new JScrollPane(jtb_wait);
	JTableHeader jth_wait = jtb_wait.getTableHeader();
	JPanel jp_second = new JPanel();
	String cols2[] = {"단톡명","현재인원"};
	String data2[][] = new String[0][2];
	DefaultTableModel dtm_room = new DefaultTableModel(data2, cols2);
	JTable jtb_room = new JTable(dtm_room);
	JScrollPane jsp_room = new JScrollPane(jtb_room);
	JTableHeader jth_room = jtb_room.getTableHeader();
	JPanel jp_second_south = new JPanel();
	JButton jbtn_create = new JButton("단톡만들기");
	JButton jbtn_in = new JButton("입장하기");
	JButton jbtn_out = new JButton("나가기");
	JButton jbtn_exit = new JButton("종료");
	JLabel jlb_banner = new JLabel();

	public WaitRoom(TalkClient tc) {
		this.tc = tc;
		initDisplay();
	}

	public void initDisplay() {
		jbtn_create.addActionListener(this);
		jbtn_in.addActionListener(this);
		jtb_room.addMouseListener(this);
		jth_wait.setBackground(Color.PINK);
		jth_wait.setForeground(Color.black);
		jtb_wait.setGridColor(Color.pink);
		jtb_wait.setSelectionBackground(Color.black);
		
		jth_wait.setReorderingAllowed(false);//??????????????????
		jp_first.setBorder(BorderFactory.createBevelBorder(0));//??????????????????
		jp_first.setBackground(Color.pink);
		jp_first.setLayout(new BorderLayout());
		jp_first.add(jsp_wait);
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
