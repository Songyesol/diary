package co.mydiary;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DiaryOracleDAO implements DAO {

		Connection conn;
		Statement stmt;
		PreparedStatement psmt;
		ResultSet rs;
		
	@Override
	public int insert(DiaryVO vo) {
		int result = 0;
		conn = JdbcUtil.connect();
		String sql = "INSERT INTO DIARY(wdate,contents) VALUES(?,?)";
	try {
		PreparedStatement psmt=conn.prepareStatement(sql);
		psmt.setString(1, vo.getWdate());
		psmt.setString(2, vo.getContents());
		result = psmt.executeUpdate();
		System.out.println(result+"건 입력완료");
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		JdbcUtil.disconnect(conn);
	}
		return result;
	}

	@Override
	public void update(DiaryVO vo) {
		int result = 0;
		conn = JdbcUtil.connect();
		String sql = "UPDATE DIARY SET CONTENTS=? WHERE WDATE=?";
		
		try {
			PreparedStatement psmt=conn.prepareStatement(sql);
			psmt.setString(1, vo.getContents());
			psmt.setString(2, vo.getWdate());
			result = psmt.executeUpdate();
			System.out.println(result+"건 수정완료");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.disconnect(psmt, conn);
		}
	}

	

	@Override
	public int delete(String date) {
		int result = 0;
		conn = JdbcUtil.connect();
		String sql = "DELETE FROM DIARY WHERE WDATE=?";
		
	try {
		PreparedStatement psmt=conn.prepareStatement(sql);
		psmt.setString(1, date);
		result = psmt.executeUpdate();
		System.out.println(result+"건 삭제완료");
		
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		JdbcUtil.disconnect(psmt, conn);
	}
		return result;
	}

	@Override
	public DiaryVO selectDate(String date) {
		DiaryVO vo =new DiaryVO();
		conn = JdbcUtil.connect();
		String sql = "SELECT * FROM DIARY WHERE WDATE = ?";
		try {
			psmt=conn.prepareStatement(sql);
			psmt.setString(1, date);
			rs=psmt.executeQuery();
			if(rs.next()) {
				vo.setContents(rs.getString("contents"));
				vo.setWdate(rs.getString("wdate"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JdbcUtil.disconnect(psmt, conn);
		}
		return vo;
	}

	@Override
	public List<DiaryVO> selectContent(String content) {
		List<DiaryVO> list = new ArrayList<DiaryVO>();
		DiaryVO vo= null;
		conn=JdbcUtil.connect();
		String sql = "SELECT * FROM DIARY WHERE CONTENTS LIKE '%'||?||'%'";
		try {
			psmt =conn.prepareStatement(sql);
			psmt.setString(1, content);
			rs = psmt.executeQuery();
			while(rs.next()) {
				vo = new DiaryVO();
				vo.setWdate(rs.getString("wdate"));
				vo.setContents(rs.getString("contents").trim());
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.disconnect(psmt, conn);
		}
		return list;
	}

	@Override
	public List<DiaryVO> selectAll() {
		List<DiaryVO> list = new ArrayList<DiaryVO>();
		DiaryVO vo = null;
		conn=JdbcUtil.connect();
		String sql = "SELECT * FROM DIARY ORDER BY WDATE";
		try {
			psmt =conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while(rs.next()) {
				vo = new DiaryVO();
				vo.setWdate(rs.getString("wdate"));
				vo.setContents(rs.getString("contents"));
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.disconnect(psmt, conn);
		}
		return list;
	}

	/*
	 * CREATE TABLE diary(
		wdate date primary key,
		contents varchar2(1000));
	 */
}
