package com.tiedate.csmis.serverdata.jxfh ;

import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tiedate.csmis.common.db.ConnectionDatabase;
import com.tiedate.csmis.common.db.SQLExecute;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 * service
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: 上海铁大电信</p>
 *
 * @author chenjingwu
 * @version 1.0
 */
public class WebServiceTerminalQuery {
  WebServiceTerminalQuery() {
  }
  private Connection conn = null ;
  private PreparedStatement ps = null ;
  private Statement st = null ;
  /**
   * 获取多媒体终端信息同步集合
   * @return list
   */
	public List<Object[]> getServiceTerminalList() {
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append("SELECT VC_ORG$ID,VC_TERM$NAME FROM SYS_T_SERVICE$TERMINAL T ");
		List<Object[]> resultList = new ArrayList<Object[]>();
		try {
			conn = ConnectionDatabase.getConnection();
			conn.setAutoCommit(false);
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sqlStr.toString());
			while (rs.next()) {
				Object[] obj = new Object[2];
				obj[0] = rs.getString("VC_ORG$ID");
				obj[1] = rs.getString("VC_TERM$NAME");
				resultList.add(obj);
			}
		} catch (Exception ex) {
			try {
				conn.rollback();
			} catch (Exception e) {
			}

			ex.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();
				}
			} catch (SQLException ex1) {
			}
		}
		return resultList;
	}
	/***
	 *  获取作业表信息
	 * @return list
	 */
		public List<Object[]> getServiceWorkList() {
			StringBuilder sqlStr = new StringBuilder();
			sqlStr.append("SELECT VC_WORK$ID,VC_EQP$NAME FROM SYS_T_SERVICE$WORK T ");
			List<Object[]> resultList = new ArrayList<Object[]>();
			try {
				conn = ConnectionDatabase.getConnection();
				conn.setAutoCommit(false);
				st = conn.createStatement();
				ResultSet rs = st.executeQuery(sqlStr.toString());
				while (rs.next()) {
					Object[] obj = new Object[2];
					obj[0] = rs.getString("VC_WORK$ID");
					obj[1] = rs.getString("VC_EQP$NAME");
					resultList.add(obj);
				}
			} catch (Exception ex) {
				try {
					conn.rollback();
				} catch (Exception e) {
				}

				ex.printStackTrace();
			} finally {
				try {
					if (ps != null) {
						ps.close();
					}
					if (conn != null) {
						conn.setAutoCommit(true);
						conn.close();
					}
				} catch (SQLException ex1) {
				}
			}
			return resultList;
		}
	/***
	 *  获取作业完成表信息
	 * @return list
	 */
	public List<Object[]> getServiceWorkCompleteList() {
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append("SELECT VC_WORK$ID,VC_REMART FROM SYS_T_SERVICE$WORKFINISH T ");
		List<Object[]> resultList = new ArrayList<Object[]>();
		try {
			conn = ConnectionDatabase.getConnection();
			conn.setAutoCommit(false);
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sqlStr.toString());
			while (rs.next()) {
				Object[] obj = new Object[2];
				obj[0] = rs.getString("VC_WORK$ID");
				obj[1] = rs.getString("VC_REMART");
				resultList.add(obj);
			}
		} catch (Exception ex) {
			try {
				conn.rollback();
			} catch (Exception e) {
			}

			ex.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();
				}
			} catch (SQLException ex1) {
			}
		}
		return resultList;
	}
		/***
		 *  设备缺陷上报
		 * @return list
		 */
		public List<Object[]> getServiceBugRptList() {
			StringBuilder sqlStr = new StringBuilder();
			sqlStr.append("SELECT * FROM SYS_T_SERVICE$BUG T");
			List<Object[]> resultList = new ArrayList<Object[]>();
			try {
				conn = ConnectionDatabase.getConnection();
				conn.setAutoCommit(false);
				st = conn.createStatement();
				ResultSet rs = st.executeQuery(sqlStr.toString());
			while (rs.next()) {
				String uid = rs.getString("N_BUG$ID");
				String staName = rs.getString("VC_STA$NAME");
				String time = rs.getString("DT_TIME");
				String report = rs.getString("VC_REPORTER");
				String type = rs.getString("VC_EQP$TYPE");
				String eName = rs.getString("VC_EQP$NAME");
				String desc = rs.getString("VC_DESCRIPTION");
				Object[] obj = new Object[10];
				obj[0] = uid;
				obj[1] = staName;
				obj[2] = time;
				obj[3] = report;
				obj[4] = type;
				obj[5] = eName;
				obj[6] = desc;
				
				resultList.add(obj);
			}
			} catch (Exception ex) {
				try {
					conn.rollback();
				} catch (Exception e) {
				}

				ex.printStackTrace();
			} finally {
				try {
					if (ps != null) {
						ps.close();
					}
					if (conn != null) {
						conn.setAutoCommit(true);
						conn.close();
					}
				} catch (SQLException ex1) {
				}
			}
			return resultList;
		}
	/**
	 *  insert 多媒体终端信息
	 * @return status
	 */
	public int addTerminalInfo(Object[] terminal) {
		int count = -1;
		String insertSql = "INSERT INTO SYS_T_SERVICE$TERMINAL (VC_ORG$ID,VC_TERM$NAME,VC_TERM$NUM) VALUES(?,?,?)";
		try {
			conn = ConnectionDatabase.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(insertSql);
			ps.setString(1, (String)terminal[0]);
			ps.setString(2, (String)terminal[1]);
			ps.setString(3, (String)terminal[2]);
			count = ps.executeUpdate();

			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e) {
			}
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();
				}
			} catch (SQLException ex1) {
			}
		}
		return count;
	}
	/**
	 *  update 多媒体终端信息
	 * @return status
	 */
	public int editTerminalInfo(Object[] terminal) {
		int count = -1;
		String insertSql = "UPDATE SYS_T_SERVICE$TERMINAL SET VC_TERM$NAME = ?, VC_TERM$NUM = ? WHERE VC_ORG$ID = ?";
		try {
			conn = ConnectionDatabase.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(insertSql);
			ps.setString(1, (String)terminal[1]);
			ps.setString(2, (String)terminal[2]);
			ps.setString(3, (String)terminal[0]);
			count = ps.executeUpdate();

			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e) {
			}
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();
				}
			} catch (SQLException ex1) {
			}
		}
		return count;
	}
	/**
	 * 修改作业提交信息
	 * @param workRpt 主表
	 * @param img 子表
	 * @param falg 判断标记
	 * @param on 开关
	 * @return status
	 */
	public int editWorkSubmitInfo(Object[] workRpt, Object[] img, boolean falg,
			boolean on) {
		String maxSql = "SELECT MAX(B.VC_WORK$ID) FROM SYS_T_SERVICE$WORK B";
		String insBugSql = "INSERT INTO SYS_T_SERVICE$WORK VALUES(?,?)";
//		String insImgSql = "INSERT INTO SYS_T_SERVICE$WORK$IMAGE (N_ID,IMGTYPE,IMGURL,VC_WORK$ID,IMGTIME)" +
//				" VALUES(sq_service$work$image.nextval,?,?,?,to_date(?, 'yyyy-mm-dd,hh24:mi:ss'))";
		String insImgSql = "INSERT INTO SYS_T_SERVICE$WORK$IMAGE (N_ID,IMGTYPE,IMGURL,VC_WORK$ID,IMGTIME,STEPNAME,REMARK,EQUNAME)" +
				" VALUES(sq_service$work$image.nextval,?,?,?,to_date(?, 'yyyy-mm-dd,hh24:mi:ss'),?,?,?)";
		int sum = 0;
		System.out.println("===LuanMa(3)?=============================" + workRpt[1]);
		try {
			conn = ConnectionDatabase.getConnection();
			conn.setAutoCommit(false);
			if (falg) {
				String cid = workRpt[0].toString();
				String delImgSql = "DELETE FROM SYS_T_SERVICE$WORK$IMAGE T WHERE T.VC_WORK$ID ='"
						+ cid +"'";
				String updBugSql = "UPDATE SYS_T_SERVICE$WORK T SET T.VC_EQP$NAME = ? WHERE T.VC_WORK$ID = ?";
				if (on) {
					SQLExecute.executeMultiSQL(new String[] { delImgSql });
					ps = conn.prepareStatement(updBugSql);
					ps.setString(1, (String) workRpt[1]);
					ps.setString(2, cid);
					ps.executeUpdate();
					conn.commit();
				}
				ps = conn.prepareStatement(insImgSql);
				ps.setString(1, (String) img[2]);
				/*Reader clobReader = new StringReader(img[3].toString());
				ps.setCharacterStream(3,clobReader,img[3].toString().length());*/

				ps.setString(2, img[3].toString());//图片直接存URL
				ps.setString(3, cid);
				ps.setString(4, (String) img[1]);
				ps.setString(5, (String) workRpt[2]);
				ps.setString(6, (String) workRpt[3]);
				ps.setString(7, (String) workRpt[1]);
				ps.executeUpdate();
				conn.commit();
				sum = 1;
			} else {
				if (on) {
					ps = conn.prepareStatement(insBugSql);
					ps.setString(1, (String) workRpt[0]);
					ps.setString(2, (String) workRpt[1]);
					ps.executeUpdate();
					conn.commit();
				}
				String cid = SQLExecute
								.executeSelectSqlAndGetFirstColValue(maxSql)
								.toString();
				ps = conn.prepareStatement(insImgSql);
				ps.setString(1, (String) img[2]);
				/*Reader clobReader = new StringReader(img[3].toString());
				ps.setCharacterStream(3,clobReader,img[3].toString().length());*/

				ps.setString(2, img[3].toString());//图片直接存URL
				ps.setString(3, cid);
				ps.setString(4, (String) img[1]);
				ps.setString(5, (String) workRpt[2]);
				ps.setString(6, (String) workRpt[3]);
				ps.setString(7, (String) workRpt[1]);
				ps.executeUpdate();
				conn.commit();
				sum = 1;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e) {
			}
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();
				}
			} catch (SQLException ex1) {
			}
		}
		return sum;
	}
	/**
	 * 修改 维修作业完成 信息
	 * @param workRpt 主表
	 * @param falg 判断标记
	 * @param on 开关
	 * @return status
	 */
	public int editWorkCompleteInfo(Object[] workRpt, boolean falg,
								  boolean on) {
		String maxSql = "SELECT MAX(B.VC_WORK$ID) FROM SYS_T_SERVICE$WORKFINISH B";
		String insBugSql = "INSERT INTO SYS_T_SERVICE$WORKFINISH VALUES(?,?)";
		int sum = 0;
		try {
			conn = ConnectionDatabase.getConnection();
			conn.setAutoCommit(false);
			if (falg) {
				String cid = workRpt[0].toString();
				String updBugSql = "UPDATE SYS_T_SERVICE$WORKFINISH T SET T.VC_REMART = ? WHERE T.VC_WORK$ID = ?";
				if (on) {
					ps = conn.prepareStatement(updBugSql);
					ps.setString(1, (String) workRpt[1]);
					ps.setString(2, cid);
					ps.executeUpdate();
					conn.commit();
				}
				sum = 1;
			} else {
				if (on) {
					ps = conn.prepareStatement(insBugSql);
					ps.setString(1, (String) workRpt[0]);
					ps.setString(2, (String) workRpt[1]);
					ps.executeUpdate();
					conn.commit();
				}
				sum = 1;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e) {
			}
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();
				}
			} catch (SQLException ex1) {
			}
		}
		return sum;
	}
	/**
	 * 修改设备缺陷上报信息
	 * @param img 子表
	 * @param falg 判断标记
	 * @param on 开关
	 * @return status
	 */
	public int editBugRptInfo(Object[] bugRpt, Object[] img, boolean falg,
			boolean on) {
		String maxSql = "SELECT MAX(B.N_BUG$ID) FROM SYS_T_SERVICE$BUG B";
		String insBugSql = "INSERT INTO SYS_T_SERVICE$BUG VALUES(sq_service$bug.nextval,?,to_date(?, 'yyyy-mm-dd hh24:mi:ss'),?,?,?,?)";
		String insImgSql = "INSERT INTO SYS_T_SERVICE$BUG$IMAGE VALUES(sq_service$bug$image.nextval,to_date(?, 'yyyy-mm-dd hh24:mi:ss'),?,?,?)";
		int sum = 0;
		try {
			conn = ConnectionDatabase.getConnection();
			conn.setAutoCommit(false);
			if (falg) {
				String cid = img[0].toString();
				String delImgSql = "DELETE FROM SYS_T_SERVICE$BUG$IMAGE T WHERE T.N_BUG$ID ="
						+ cid;
				if (on) {
					SQLExecute.executeMultiSQL(new String[] { delImgSql });
				}
				ps = conn.prepareStatement(insImgSql);
				ps.setString(1, (String) img[1]);
				ps.setString(2, (String) img[2]);
				Reader clobReader = new StringReader(img[3].toString()); 
				ps.setCharacterStream(3,clobReader,img[3].toString().length());
				ps.setInt(4, Integer.parseInt(cid));
				ps.executeUpdate();
				conn.commit();
				sum = 1;
			} else {
				if (on) {
					ps = conn.prepareStatement(insBugSql);
					ps.setString(1, (String) bugRpt[0]);
					ps.setString(2, (String) bugRpt[1]);
					ps.setString(3, (String) bugRpt[2]);
					ps.setString(4, (String) bugRpt[3]);
					ps.setString(5, (String) bugRpt[4]);
					ps.setString(6, (String) bugRpt[5]);
					ps.executeUpdate();
					conn.commit();
				}
				int sid = Integer
						.parseInt(SQLExecute
								.executeSelectSqlAndGetFirstColValue(maxSql)
								.toString());
				ps = conn.prepareStatement(insImgSql);
				ps.setString(1, (String) img[1]);
				ps.setString(2, (String) img[2]);
				Reader clobReader = new StringReader(img[3].toString()); 
				ps.setCharacterStream(3,clobReader,img[3].toString().length());
				ps.setInt(4, sid);
				ps.executeUpdate();
				conn.commit();
				sum = 1;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e) {
			}
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();
				}
			} catch (SQLException ex1) {
			}
		}
		return sum;
	}
	/**
	 *  按ID删除多媒体终端信息
	 * @param id
	 * @return status
	 */
	public int deleteTerminalInfo(String id) {
		int count = -1;
		String insertSql = "DELETE FROM SYS_T_SERVICE$TERMINAL WHERE VC_ORG$ID = ?";
		try {
			conn = ConnectionDatabase.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(insertSql);
			ps.setString(1, id);
			count = ps.executeUpdate();

			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e) {
			}
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();
				}
			} catch (SQLException ex1) {
			}
		}
		return count;
	}
}
