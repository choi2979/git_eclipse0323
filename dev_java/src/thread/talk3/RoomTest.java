package thread.talk3;

import java.util.ArrayList;
import java.util.List;
import basic.inout.PrintfTest;

public class RoomTest {
	TalkServer ts = null;
	//축구부 명단 출력하기
	void printFList(List<String> nameList) {
		if(nameList != null) {
			for(int i = 0; i<nameList.size(); i++) {
				System.out.println(nameList.get(i));
			}			
		}
		else {
			System.out.println("명단이 없습니다.");
		}
		
	}
	public void roomCasting(String msg, String roomTitle) {
		for(int i = 0; i<ts.roomList.size();i++) {
			//Room에 대한 주소번지를 가져옴.
			Room room = ts.roomList.get(i);
			if(roomTitle.equals(room.title)) {
				for(int j = 0; j<room.userList.size();j++) {
					TalkServerThread tst = room.userList.get(j);
					try {
						tst.send(msg);
					} catch (Exception e) {
						System.out.println(e.toString());
					}
				}
			}
		}
		//방의 갯수는 어떻게 알 수 있을까?
		
		//그 방중에 방이름은 어떻게 비교하지?
		
		//그 방에 있는 사람 수는 어떻게 아는 거지?
	}
	public static void main(String[] args) {
		RoomTest rt = new RoomTest();
		Room broom = new Room();
		broom.title = "농구부";
		broom.nameList.add("김");
		broom.nameList.add("이");
		broom.nameList.add("박");
		broom.nameList.add("최");
		broom.nameList.add("정");
		Room froom = new Room();
		froom.title = "축구부";
		froom.nameList.add("장");
		froom.nameList.add("임");
		froom.nameList.add("강");
		rt.printFList(froom.nameList);

	}

}
