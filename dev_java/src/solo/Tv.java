package solo;

import java.util.Scanner;

public class Tv {
	int channel = 2;
	boolean power= false;
	String channelName[] = {"Btv","tvn","bbc","jtbc","CCTV","SBS","KBS2","YTN","KBS","EBS","MBC"};
	//전역변수는 그 클래스가 활동 중에는 계속 유지 됩니다.
	
	/*
	 * 메소드의 파라미터자리는 사용자가 선택한 값, 입력한 값 등을 받아오는 자리 입니다.
	 * u_power=true기 저장됨
	 * 그 변수에 not이 있으므로 반대인 false변환 후 대입된다.
	 * 파라미터가 있어야 내 의사를 표현할 수 있다.=>소통시작
	 * false->true
	 * TV tv = new TV();
	 * tv.power()
	 * power = !power
	 */
	void power() {
		
		Scanner scan = new Scanner(System.in);
		boolean power = scan.nextBoolean();
			if(power==true) {
				System.out.println("TV ON"); 
			}
			if(power==false) {
				System.out.println("TV OFF"); 
			}
		
	}
	
	void channelUp() {
		
		++channel;
		System.out.print(channel);
	
	}
	
	void channelDown() {
		--channel;
		System.out.print(channel);
		
	}

	public static void main(String[] args) {
		Tv tv = new Tv();
		
		tv.power();
		for(;;) {
		Scanner scan = new Scanner(System.in);
		String cc = scan.nextLine();
		
		switch(cc) {
			case "+":
				tv.channelUp(); System.out.println(" "+tv.channelName[tv.channel]); break;
			case "-":
				tv.channelDown(); System.out.println(" "+tv.channelName[tv.channel]); break;
				
			}
		}
		

	}

}
