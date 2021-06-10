package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dto.TblAttach;
import jdbc.JdbcUtil;

public class TblAttachDao {

	public int insert(Connection conn, TblAttach tblAttach) throws SQLException {
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("insert into tbl_attach "
					+ "(fullName, bno) values (?,?)");
			pstmt.setString(1, tblAttach.getFullName());
			pstmt.setInt(2, tblAttach.getBno());
			int insertedCount = pstmt.executeUpdate();

			return insertedCount;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
			JdbcUtil.close(pstmt);
		}
	}


	public TblAttach selectById(Connection conn, int no) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(
					"select * from tbl_attach where bno = ?");
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			TblAttach article = null;
			return article;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}	

}
