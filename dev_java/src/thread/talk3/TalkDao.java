package thread.talk3;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class TalkDao {
	Connection 			con = null; // 물리적으로 떨어져 있는 서버와 연결통로 만들기
	PreparedStatement pstmt = null;//쿼리문을 요청할 때-오라클
	PreparedStatement pstmt1 = null;//쿼리문을 요청할 때-오라클
	ResultSet			rs = null;
	CallableStatement cstmt = null;
	DBConnectionMgr dbMgr = DBConnectionMgr.getInstance();
	public String loginProc(String mem_id, String mem_pw) {
		String mem_name = null;
		try {
			con = dbMgr.getConnection();
			cstmt = con.prepareCall("{call proc_login(?,?)}");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mem_name;
	}
	
	public String login(String mem_id, String mem_pw) {
		String mem_name = null;
		StringBuilder isID = new StringBuilder();
		StringBuilder sql = new StringBuilder();
		int status = 2;
		try {
			isID.append("SELECT NVL(                                       ");
			isID.append("	    (                                          ");
			isID.append("	    SELECT 1 FROM dual                         ");
			isID.append("	    WHERE EXISTS (SELECT mem_name FROM member5 ");
			isID.append("	    WHERE mem_id=?)                       ");
			isID.append("	    ),-1) isID FROM dual");
		    con = dbMgr.getConnection();
		    pstmt = con.prepareStatement(isID.toString());
		    pstmt.setString(1, mem_id);
		    rs = pstmt.executeQuery();
		    
		    if(rs.next()) {
		    status = rs.getInt("isID");
		    }
		    if(status==1) {//아이디가 존재하면 비번을 비교한다.
		    	sql.append("SELECT mem_name FROM member5");
		        sql.append(" WHERE mem_id =?          ");
		        sql.append(" AND mem_pw =?            ");
		        pstmt1 = con.prepareStatement(sql.toString());
		        pstmt1.setString(1, mem_id);
		        pstmt1.setString(2, mem_pw);
		        rs = pstmt1.executeQuery();
		        if(rs.next()) {
		        	mem_name = rs.getString("mem_name");
		        } 
		        else {
		        	return "비밀번호가 맞지 않습니다.";
		        }
		        return mem_name;
		    }
		    else {//아이디가 존재하지 않으면 비번을 비교한다.
		    	return "아이디가 존재하지 않습니다.";
		    }
		    //System.out.println("status: "+status);
		    
		} catch (SQLException se) {
			
		} catch (Exception e) {
			System.out.println("sql: "+isID.toString());
		} 
		return mem_name;
	}
}
