package solo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConnectionMgr;

import design.book.BookVO;

public class BookDao {
	DBConnectionMgr dbMgr = DBConnectionMgr.getInstance();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	public List<BookVO> bookList(BookVO pbVO) {
		List<BookVO> bookList = new ArrayList<BookVO>();
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("SELECT b_no, b_title, b_author, b_publish");
		    sql.append(" FROM book2020                           ");
		    sql.append(" ORDER BY b_no desc                      ");
		    con = dbMgr.getConnection();
		    pstmt = con.prepareStatement(sql.toString());
		    rs = pstmt.executeQuery();
		    BookVO rbVO = null;
		    while(rs.next()) {
		    	rbVO = new BookVO();
		    	rbVO.setB_no(rs.getInt("b_no"));
		    	rbVO.setB_title(rs.getString("b_title"));
		    	rbVO.setB_author(rs.getString("b_author"));
		    	rbVO.setB_publish(rs.getString("b_publish"));
		    	bookList.add(rbVO);
		    }
		} catch (SQLException se) {
			System.out.println(se.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookList;
	}
	
	public BookVO bookDetail(BookVO pbVO) {
		BookVO rbVO = null;
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("SELECT b_no, b_title, b_author, b_publish, b_info, b_img");
		    sql.append(" FROM book2020                           ");
		    sql.append(" WHERE b_no=?");
		    con = dbMgr.getConnection();
		    pstmt = con.prepareStatement(sql.toString());
		    pstmt.setInt(1, pbVO.getB_no());
		    rs = pstmt.executeQuery();
		    if(rs.next()) {
		    	rbVO = new BookVO();
		    	rbVO.setB_no(rs.getInt("b_no"));
		    	rbVO.setB_title(rs.getString("b_title"));
		    	rbVO.setB_author(rs.getString("b_author"));
		    	rbVO.setB_publish(rs.getString("b_publish"));
		    	rbVO.setB_info(rs.getString("b_info"));
		    	rbVO.setB_img(rs.getString("b_img"));
		    }
		} catch (SQLException se) {
			System.out.println(se.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rbVO;
	}
	
	public int bookInsert(BookVO pbVO) {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("INSERT into book2020(b_no,b_title,b_author,b_publish,b_info) ");
			sql.append(" VALUES(seq_book_no.nextval,?,?,?,?) ");
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			
			int i = 1;
			pstmt.setString(i++, pbVO.getB_title());
			pstmt.setString(i++, pbVO.getB_author());
			pstmt.setString(i++, pbVO.getB_publish());
			pstmt.setString(i++, pbVO.getB_info());
			result = pstmt.executeUpdate();
			System.out.println("result:"+result);
			System.out.println("bookInsert 호출");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}
	public int bookUpdate(BookVO pbVO) {
		System.out.println("bookUpdate");
		int result = 0;
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("update book2020 SET b_title=?, b_author=?,b_publish=?");
			sql.append("		WHERE b_no=?                                 ");
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			int i = 1;
			pstmt.setString(i++, pbVO.getB_title());
			pstmt.setString(i++, pbVO.getB_author());
			pstmt.setString(i++, pbVO.getB_publish());
			pstmt.setInt(i++, pbVO.getB_no());
			result = pstmt.executeUpdate();
			System.out.println(result);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}

	public int bookDelete(BookVO pbVO) {
		int result = 0;
		int cnt = 0;
		StringBuilder sql = new StringBuilder();
		try {
			if(pbVO.getBnos()!=null) {
				cnt = pbVO.getBnos().size();
			}
			sql.append("DELETE FROM book2020 WHERE b_no IN(");
			for(int x = 0; x<cnt; x++) {
				if(x==cnt-1) {
					sql.append("?)");
				}else {
					sql.append("?,");
				}
			}
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			int no = 0;
			List<Integer> bnos = pbVO.getBnos();
			for(int j=0; j<cnt; j++) {
				pstmt.setInt(++no, bnos.get(j));
			}
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}
}
