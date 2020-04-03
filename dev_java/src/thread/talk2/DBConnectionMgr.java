package thread.talk2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import oracle.jdbc2.JDBCTest;

public class DBConnectionMgr {
	Connection 			con = null;
	
	public static final String _DRIVER = "oracle.jdbc.driver.OracleDriver";
	
	//물리적으로 떨어져 있는 오라클 서버에 URL정보 추가
	public static final String _URL = "jdbc:oracle:thin:@192.168.0.119:1521:orcl11";
	
	public static String _USER = "bank";
	public static String _PW = "tiger";
	//static-클래스급이다. 공유(하나다)
	private static DBConnectionMgr dbMgr = null;
	private DBConnectionMgr() {}
	//싱글톤 패턴으로 객체 관리하기 - 인스턴스화 과정이다.
	public static DBConnectionMgr getInstance() {
		if(dbMgr == null) {
			dbMgr = new DBConnectionMgr();
		}
		return dbMgr;
	}
	//
	//인스턴스화를 해주는 메소드 구현
	public Connection getConnection() {
		System.out.println("getConnection호출 성공");
		//오라클 회사 정보를 수집함.
		try {
			Class.forName(_DRIVER);
			//con = new Connection();반드시 구현체 클래스가 있어야 한다.
			con = DriverManager.getConnection(_URL, _USER, _PW);
			
		} catch (ClassNotFoundException ce) {
			System.out.println("드라이버 클래스 이름을 찾을 수 없어요.");
		} catch(Exception e) {
			System.out.println("예외가 발생했음. 정상적으로 처리가 안됨.");
		}
		
		
		return con;
	}		
		
		/*
		 * DBConnectionMgr은 여러 업무에서 공통으로 사용하는 클래스 입니다.
		 * 사용한 자원(Connection, PreparedStatement, ResultSet)은
		 * 반드시 반납을 하도록 합니다.
		 * 동시 접속자 수가 많은 시스템에서 자원사용은 곧 메모리랑 직결되므로
		 * 서버가 다운되거나 시스템 장애 발생에 원인이 됩니다.
		 * 
		 */
	//자바에서는 같은 이름의 메소드를 여러개 만들 수 있다.
	//1)메소드 오버로딩
	//2)메소드 오버라이딩
	public void freeConnection(Connection con, PreparedStatement pstmt) {
		try {
			if(pstmt!=null) {
				pstmt.close();
			}
			if(con!=null) {
				con.close();
			}
		} catch (Exception e) {
			System.out.println("Exception : " + e.toString());
		}
	}
		public void freeConnection(Connection con, PreparedStatement pstmt, ResultSet rs) {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(pstmt!=null) {
					pstmt.close();
				}
				if(con!=null) {
					con.close();
				}
			} catch (Exception e) {
				System.out.println("Exception : " + e.toString());
			}
		}
}
	


