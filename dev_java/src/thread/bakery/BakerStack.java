package thread.bakery;

import java.util.Vector;

public class BakerStack {
	private Vector<String> breads = new Vector<>();//breads 벡터 생성
	//빵을 가져가는 메소드
	public synchronized String pop() {//순서를 정해주는
		String bread = null;//bread null
		while(breads.size()==0) {
			try {
				System.out.println("빵이 곧 만들어집니다. 잠시만 기다려주세요.");
				this.wait();//wait() 메서드를 호출하면 락을 해제하고, 스레드는 잠이 든다. 누군가 깨워줄 때 까지 wait()은 리턴되지 않는다.
			} catch (Exception e) {
				System.out.println("여기....새치기");
			}
		}
		bread = breads.remove(breads.size()-1);//breads배열 안에 값을 bread에 넣고 삭제한다.
		
		return bread;
	}
	//빵을 진열하는 메소드
	//synchronized-순서를 침해받지 않게 해준다.
	public synchronized void push(String bread) {
		System.out.println("오래 기다리셧습니다. 빵을 가져가세요.");
		this.notify();//잠자는 스레드를 다시 깨운다.
		breads.add(bread);//파라미터로 받은 bread값을 Vector breads에 담는다.
	}
}
