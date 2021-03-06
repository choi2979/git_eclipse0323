package thread.talk2;

import java.util.StringTokenizer;
import java.util.Vector;

public class TalkClientThread extends Thread {
	TalkClient tc = null;
	public TalkClientThread(TalkClient tc) {
		this.tc = tc;
	}
	/*
	 * 서버에서 말한 내용을 들어봅시다.
	 */
	public void run() {
		boolean isStop = false;
		while(!isStop) {
			try {
				String msg = "";
				msg = (String)tc.ois.readObject();
				//tc.jta_display.append(msg+"\n");
				StringTokenizer st = null;
				int protocol = 0;
				if(msg != null) {
					st = new StringTokenizer(msg,"#");
					protocol = Integer.parseInt(st.nextToken());//100
				}
				switch(protocol) {
					case Protocol.LOGIN:{//100#apple
						String nickName = st.nextToken();
						tc.jta_display.append(nickName+"닝이 입장하였습니다.\n");
						Vector<String> v = new Vector<String>();
						v.add(nickName);
						tc.dtm.addRow(v);
					}break;
					case Protocol.ONE:{
						String nickName = st.nextToken();
						String otherName = st.nextToken();
						String message = st.nextToken();
						tc.jta_display.append(nickName+"님이"+otherName+"님에게 "+message+"\n");
						tc.jta_display.setCaretPosition(tc.jta_display.getDocument().getLength());
					}break;
					case Protocol.MULTI:{
						String nickName = st.nextToken();
						String message = st.nextToken();
						tc.jta_display.append("["+nickName+"]"+message+"\n");
						tc.jta_display.setCaretPosition(tc.jta_display.getDocument().getLength());
					}break;
					case Protocol.CHANGE:{
						String nickName = st.nextToken();
						String afterName = st.nextToken();
						String message = st.nextToken();
						//테이블의 대화명 변경하기
						for(int i=0;i<tc.dtm.getRowCount();i++) {
							String imsi = (String)tc.dtm.getValueAt(i, 0);
							if(nickName.equals(imsi)) {
								tc.dtm.setValueAt(afterName, i, 0);
								break;
							}
						}
						//채팅창에 타이틀바에도 대화명을 변경처리 한다.
						if(nickName.equals(tc.loginForm.nickName)) {
							tc.setTitle(afterName+"님의 대화창");
							tc.loginForm.nickName = afterName;
						}
						tc.jta_display.append(message+"\n");
					}break;
					case Protocol.EXIT:{
						String nickName = st.nextToken();
						tc.jta_display.append(nickName+"님이 퇴장 하였습니다.\n");
						tc.jta_display.setCaretPosition(tc.jta_display.getDocument().getLength());
						for(int i=0;i<tc.dtm.getRowCount();i++) {
							String n = (String)tc.dtm.getValueAt(i, 0);
							if(n.equals(nickName)) {
								tc.dtm.removeRow(i);
							}
						}
						
					}break;
				
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}////////////////while
	}//////////////////////////////end of run
}
