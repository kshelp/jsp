package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.TblAttachDao;
import dto.TblAttach;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class FileService {

	private TblAttachDao tblAttachDao = new TblAttachDao();  // 필드
	
	public void upload(List<TblAttach> tblAttachList) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			for(int i=0; i<tblAttachList.size(); i++) {
				tblAttachDao.insert(conn, tblAttachList.get(i));
			}
			
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	public TblAttach getArticle(int articleNum, boolean increaseReadCount) {
		try (Connection conn = ConnectionProvider.getConnection()){
			TblAttach article = tblAttachDao.selectById(conn, articleNum);
			return article;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
