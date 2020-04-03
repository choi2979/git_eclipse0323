package thread.emoticon;

public class TalkDaoTest {

	public static void main(String[] args) {
		TalkDao td = new TalkDao();
		String result = td.login("sk", "123");
		System.out.println("result:"+result);
		

	}

}
