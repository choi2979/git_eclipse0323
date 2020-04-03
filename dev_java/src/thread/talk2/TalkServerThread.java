package thread.talk2;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

public class TalkServerThread extends Thread {
	public TalkServer ts = null;
	Socket client = null;
	ObjectOutputStream 	oos = null;
	ObjectInputStream 	ois = null;
	String chatName = null;
	public TalkServerThread(TalkServer ts) {
		this.ts = ts;
		this.client = ts.socket;
		try {
			oos = new ObjectOutputStream(client.getOutputStream());
			ois = new ObjectInputStream(client.getInputStream());
			
			String msg = (String)ois.readObject();
			ts.jta_log.append(msg+"\n");
			StringTokenizer st = new StringTokenizer(msg,"#");
			st.nextToken();//100
			chatName = st.nextToken();
			ts.jta_log.append(chatName+"님이 입장하였습니다.\n");
			for(TalkServerThread tst: ts.globalList) {
			//이전에 입장해 있는 친구들 정보 받아내기
				String currentName = tst.chatName;
				this.send(Protocol.LOGIN+Protocol.SEPER+currentName);
			}
			//현재 서버에 입장한 클라이언트 스레드 추가하기
			ts.globalList.add(this);
			this.broadCasting(msg);
			
//			//JTextArea에 현재 입장한 클라이언트 스레드 이름 출력하기
//			ts.jta_log.append("this.getName()"+this.getName()+"\n");
//			//나 전에 입장한 사람이 한 명도 없을 때는 아래 for문 실행기회 없음.
//			//내가 입장하기 전에 입장한 클라이언트 스레드 이름 출력하기
//			for(TalkServerThread tst:ts.globalList) {
//				ts.jta_log.append("tst.getName()"+tst.getName()+"\n");
//			}
			//현재 서버에 입장한 클라이언트 스레드 추가하기
//			ts.globalList.add(this);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
	}
	
	//현재 입장해 있는 친구들 모두에게 메시지 전송하기
	private void broadCasting(String msg) {
		for(TalkServerThread tst:ts.globalList) {
			tst.send(msg);
		}
		
	}
	
	//클라이언트에게 말하기
	private void send(String msg) {
		try {
			oos.writeObject(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void run() {
		String msg = null;
		boolean isStop = false;
		try {
			run_start:
			while(!isStop) {
				msg = (String)ois.readObject();
				ts.jta_log.append(msg+"\n");
				ts.jta_log.setCaretPosition(ts.jta_log.getDocument().getLength());
				StringTokenizer st = null;
				int protocol = 0;
				if(msg != null) {
					st = new StringTokenizer(msg,"#");
					protocol = Integer.parseInt(st.nextToken());//100
				}
				switch(protocol) {
				case Protocol.ONE:{
					//보내는 사람
					String nickName = st.nextToken();
					//받는 사람
					String otherName = st.nextToken();
					//보내진 메시지
					String msg1 = st.nextToken();
					//클라이언트로 전송하기
					//스레드 중에서 상대 스레드에게만 메세지 전송할 것
					for(TalkServerThread tst:ts.globalList) {
						if(otherName.equals(tst.chatName)) {//내가 선택한 상대가 맞는거야?
							tst.send(Protocol.ONE
									+"#"+nickName
									+"#"+otherName
									+"#"+msg1);
							break;
						}
					}
					//그리고 나 자신에게도 전송해 보자.
					this.send(Protocol.ONE
							+"#"+nickName
							+"#"+otherName
							+"#"+msg1);
					
				}break;
				case Protocol.MULTI:{
					String nickName = st.nextToken();
					String message = st.nextToken();
					broadCasting(Protocol.MULTI
							+"#"+nickName
							+"#"+message);
				}break;
				case Protocol.CHANGE:{
					String nickName = st.nextToken();
					String afterName = st.nextToken();
					String message = st.nextToken();
					this.chatName = afterName;
					broadCasting(Protocol.CHANGE
							+"#"+nickName
							+"#"+afterName
							+"#"+message);
				}break;
				case Protocol.EXIT:{
					String nickName = st.nextToken();
					ts.globalList.remove(this);
					
					broadCasting(Protocol.EXIT
							+"#"+nickName);
				}break run_start;
				}////////////end of switch
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		//while(true) {무한루프에 빠질 수 있다.
	}
}
