package solo;

import java.util.Random;
import java.util.Scanner;

public class BaseBallGame {
	int com[] = new int[3];
	int my[] = new int[3];
	
	public void randomNum() {
		Random random = new Random();//0.0~
		//0.0~10.0
		com[0] = random.nextInt(10);
		do {
			com[1] = random.nextInt(10);
		}while(com[0]==com[1]);
		do {
			com[2] = random.nextInt(10);
		}while(com[0]==com[2]||com[1]==com[2]);//첫번째 방값과 세번째 채번한 숫자가 같니? || 두번째방 값과 세번째 채번한 숫자가 같니?
	}//전역변수에 값이 저장됨
	
	//insert here account메소드 구현///////////////////////////////////////////////
	public String account(String user) {
		int temp = Integer.parseInt(user);
		my[0] = temp/100;//123/100=1
		my[1] = (temp%100)/10;//2
		my[2] = temp%10;//3
		for(int me:my) {
			System.out.println(me);
		}
		int strike = 0;
		int ball = 0;
		
		for(int i=0;i<com.length;i++) {
			for(int j=0;j<my.length;j++) {
				
				if(com[i]==my[j]) {//내가 입력한 숫자중에 컴터에 그 숫자가 있니?
					if(i==j) {//혹시 그 숫자가 자리도 일치하는거야?
						strike++;
					}
					else {
						ball++;
					}
				}
			}
			
		}
		if(strike==3) {
			return "정답입니다. 축하합니다.";
		}
		return strike+"스"+ball+"볼";
	}
	
	///////////////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		BaseBallGame bbg = new BaseBallGame();		
		bbg.randomNum();
		//System.out.println(bbg.com[0]+""+bbg.com[1]+""+bbg.com[2]);
		System.out.println("게임이 시작되었습니다");
		
		int cnt = 0;
		while(cnt<9) {
			System.out.println("세자리 숫자를 입력하세요.");
			Scanner scan = new Scanner(System.in);
			String user = null;
			user = scan.nextLine();
		cnt++;	
		String result = bbg.account(user);
		System.out.println("사용자가 입력한 값은 "+user);
		System.out.println(user+":"+result);
		}

	}

}
