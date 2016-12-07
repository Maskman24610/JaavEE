package org.dbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class edit
 */
@WebServlet("/edit")
public class edit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn;
	private PrintWriter pw;
	private String[] fields={"座號","姓名","地址","hobby","passwd"};
       
    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	super.init();
    	try{
    		Class.forName("com.mysql.jdbc.Driver");
    		Properties prop=new Properties();
    		prop.setProperty("user", "root");
    		prop.setProperty("password","root");
    		
    		conn=DriverManager.getConnection("jdbc:mysql://localhost/school?&characterEncoding=Utf-8",prop);
    	}
    	catch(Exception e){
    		System.out.println(e.toString());
    	}
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	   response.setContentType("text/html;charset=Utf-8");
	   request.setCharacterEncoding("Utf-8");
	   response.setCharacterEncoding("Utf-8");
	   
	   pw=response.getWriter();
	   
	   String editid=request.getParameter("editid");
	   
	   outHtml(queryData(editid));
	}

    private HashMap<String,String> queryData(String 座號){
    	HashMap<String,String> row=new HashMap<>();
    	try{
    		PreparedStatement pstmt=conn.prepareStatement("select * from 學生 "+" where 座號=?");
    		pstmt.setString(1, 座號);
    		ResultSet rs=pstmt.executeQuery();
    		rs.first();
    		for(String field:fields){
    			row.put(field, rs.getString(field));
    		}
    	}
    	catch(Exception e){
    		System.out.println(e.toString());
    	}
    	return row;
    	
    }	
	
   //顯示修改頁面的方法
	private void outHtml(HashMap<String,String> row){
		pw.println("<meta http-equiv='Content-Type' content='text/html;charset=utf-8'>");
		pw.println("<form action='controller'>");
		pw.print(String.format("<input type='hidden' name='updateid' value='%s'>",row.get(fields[0])));
		pw.print(String.format("姓名:<input type='text' name='姓名' value='%s'><br />",row.get(fields[1])));
		pw.print(String.format("地址:<textarea row='1' colume='100' name='地址' >%s</textarea><br />",row.get(fields[2])));
		pw.print(String.format("Hobby:<input type='text' name='hobby' value='%s'><br />",row.get(fields[3])));
		pw.print(String.format("Password:<input type='password' name='passwd' value='%s'> <br />",row.get(fields[4])));
		pw.print("<input type='submit' name='type' value='edit'>");
	}

}
