package com.tiedate.csmis.common.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tiedate.csmis.common.constants.SystemConstants;

/**
 * Servlet implementation class InitAllServlet
 * <p/>
 * 初始化系统参数 create by WTH on 2016/6/17
 */
public class InitAllServlet extends HttpServlet {
	@Autowired private JdbcTemplate jdbcTemplate;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InitAllServlet() {
		initAll();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/***
	 * 初始化一些系统参数
	 */
	private void initAll() {

		String sqlStr = "SELECT VC_SERVER$ID,N_LEVEL, VC_SERVER$NAME FROM SYS_T_ORG$LEVEL WHERE VC_IS$SERVER = '1'";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlStr);
		Map<String, Object> map = list.get(0);
		SystemConstants.SERVER_ID = map.get("VC_SERVER$ID").toString();
		SystemConstants.SERVER_NAME = map.get("VC_SERVER$NAME").toString();
		SystemConstants.SERVER_LEVEL = map.get("N_LEVEL").toString();
		
		System.out.println("-"+map.get("VC_SERVER$ID").toString());
		System.out.println("-"+map.get("VC_SERVER$NAME").toString());
		System.out.println("-"+map.get("N_LEVEL").toString());
	}

}
