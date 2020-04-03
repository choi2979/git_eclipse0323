package thread.talk3;

import java.awt.Color;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class TalkClientThread extends Thread {
	TalkClientVer2 tc = null;
	public TalkClientThread(TalkClientVer2 tc) {
		this.tc = tc;
	}
	public SimpleAttributeSet makeAttribute(String fcolor) {
		SimpleAttributeSet sas = new SimpleAttributeSet();
		sas.addAttribute(StyleConstants.ColorConstants.Foreground
				, new Color(Integer.parseInt(fcolor)));
		return sas;
	}
	/*
	 * 서버에서 말한 내용을 들어봅시다.
	 */
	public void run() {
		String msg = null;
		boolean isStop = false;
		while(!isStop) {
			try {
				msg = (String)tc.ois.readObject();
				StringTokenizer st = null;
				int protocol = 0;
				if(msg != null) {
					st = new StringTokenizer(msg, Protocol.SEPER);
					protocol = Integer.parseInt(st.nextToken());
				}
				switch(protocol) {
					case Protocol.WAIT:{
					String nickName = st.nextToken();
					String state = st.nextToken();
					Vector<String> v_nick = new Vector<String>();
					v_nick.add(nickName);//나신입
					v_nick.add(state);//대기
					tc.wr.dtm_wait.addRow(v_nick);
					}break;
					case Protocol.ROOM_CREATE:{
					String roomTitle = st.nextToken();
					String currentNum = st.nextToken();
					Vector<String> v_room = new Vector<String>();
					v_room.add(roomTitle);
					v_room.add(currentNum);
					tc.wr.dtm_room.addRow(v_room);
					}break;
					case Protocol.ROOM_LIST:{//나중에 들어온 사람에게 업데이트 시키기
						String roomTitle = st.nextToken();
						String currentNum = st.nextToken();
						Vector<String> v_room = new Vector<String>();
						v_room.add(roomTitle);
						v_room.add(currentNum);
						tc.wr.dtm_room.addRow(v_room);
					}break;
					case Protocol.ROOM_IN: {
						String roomTitle = st.nextToken();
						String currnet = st.nextToken();
						String nickName = st.nextToken();
						//방정보 인원 갱신
						for(int i=0;i<tc.wr.jtb_room.getRowCount();i++) {
							if(roomTitle.equals(tc.wr.dtm_room.getValueAt(i, 0))) {
								tc.wr.dtm_room.setValueAt(currnet, i, 1);
								break;
							}
						}
						//대기실 위치 갱신
						for(int i=0;i<tc.wr.jtb_wait.getRowCount();i++) {
							if(nickName.equals((String)tc.wr.dtm_wait.getValueAt(i, 0))) {
								tc.wr.dtm_wait.setValueAt(roomTitle, i, 1);
							}
						}
						//대화방으로 이동하기 -MessageRoom화면으로 이동
						//방입장하기 버튼을 누른 사람만 화면 이동처리
						if(tc.nickName.equals(nickName)) {
							tc.jtp.setSelectedIndex(1);//0:waitroom 1:MessageRoom
							for(int x=0;x<tc.wr.jtb_wait.getRowCount();x++) {
								if(roomTitle.equals(tc.wr.dtm_wait.getValueAt(x, 1))) {
									String imsi[] = {(String)tc.wr.dtm_wait.getValueAt(x, 0)};
									tc.mr.dtm_nick.addRow(imsi);
								}///////단톡이름 비교
							}
						}//////////////이름 비교
					}break;
					case Protocol.ROOM_INLIST:{
						String roomTitle = st.nextToken();
						String currentNum = st.nextToken();
						String nickName = st.nextToken();
						Vector<String> v_room = new Vector<>();
						v_room.add(nickName);
						tc.mr.dtm_nick.addRow(v_room);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}////////////////while
	}//////////////////////////////end of run
}
