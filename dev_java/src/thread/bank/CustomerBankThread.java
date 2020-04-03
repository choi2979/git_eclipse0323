package thread.bank;

import java.util.StringTokenizer;
import java.util.Vector;

public class CustomerBankThread extends Thread {
	CustomerBank cb = null;//3
	public CustomerBankThread(CustomerBank cb) {//2
		this.cb = cb;
	}
	public void run() {//1
		String msg = null;
		boolean isStop = false;
		while(!isStop) {
			try {
				//100#진아
				msg = (String)cb.ois.readObject();
				cb.jta_clog.append(msg+"\n");
				StringTokenizer st = null;
				int protocol = 0;
				if(msg != null) {
					st = new StringTokenizer(msg, "#");
					protocol = Integer.parseInt(st.nextToken());
				}
				switch(protocol) {
					case 100:{
						String nickName = st.nextToken();
						cb.jta_clog.append(nickName+"님이 입장하였습니다.");
						Vector<String> v = new Vector<String>();
						v.add(nickName);
						cb.dtm_nickName.addRow(v);
					}break;
				}
			} catch (Exception e) {
				System.out.println("[[Exception]]"+e.toString());
			}///////////////////////end of try
		}////////////////end of while
	}//////////////end of run

}
