package org.dbc;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class controller
 */
@WebServlet("/controller")
public class controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Connection conn;
    private PrintWriter pw;
    private String[] fields={"座號","姓名","地址","hobby","passwd"};
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	   response.setContentType("text/html;charset=utf-8");
	   request.setCharacterEncoding("utf8");
	   response.setCharacterEncoding("Utf8");
	   
	   //轉表單資料給Edit.html
	  
	   
	   pw=response.getWriter();
	   dbcontrol db=new dbcontrol();
	   String type=request.getParameter("type");
	   String deleteid=request.getParameter("deleteid");
	   if(type!=null&&type.equals("Add")){
		   String 姓名=request.getParameter(fields[1]);
		   String 地址=request.getParameter(fields[2]);
		   String hobby=request.getParameter(fields[3]);
		   String passwd=request.getParameter(fields[4]);
		   db.addData(姓名, 地址, hobby, passwd);
	   }
	   else if(deleteid!=null){
		   db.deleteData(deleteid);
	   }
	   else if(type!=null&&type.equals("edit")){
		   String updateid=request.getParameter("updateid");
		   String 姓名=request.getParameter(fields[1]);
		   String 地址=request.getParameter(fields[2]);
		   String hobby=request.getParameter(fields[3]);
		   String passwd=request.getParameter(fields[4]);
		   db.editData(updateid,姓名,地址,hobby,passwd);
	   }
	   outHtml(db.queryData());
	}
	private String loadView(String file){
		String uploadPath=getServletContext().getInitParameter("upload-path");
		File uploadFile=new File(uploadPath,file);
		
		long len=uploadFile.length();
        byte[] buf=new byte[(int) len];
        
        try{
        	BufferedInputStream bin=new BufferedInputStream(new FileInputStream(uploadFile));
            bin.read(buf);
            bin.close();
        }
        catch(Exception e){
        	System.out.println(e.toString());
        }
        
		return new String(buf);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
    private void outHtml(ArrayList<HashMap<String,String>> data){
       String htmlView=loadView("view.html");
       pw.print("<script> function isDelete( 姓名){return confirm('Delete'+姓名+'?');}</script>");
 	   pw.print(htmlView);
 	   for(HashMap<String,String> row:data){
 		   pw.print("<tr>");
 		   for(String field:fields){
 			   pw.print(String.format("<td>%s",row.get(field)));
 		   }
 		  pw.print(String.format("<td><a href=?deleteid=%s  onclick=' return isDelete(\"%s\")'>Delete</a>"
				   ,row.get(fields[0]),row.get(fields[1])));
 	     pw.print(String.format("<td><a href='edit?editid=%s'>Edit</a>"
 	    		 ,row.get(fields[0]))); 
       }
 	   pw.print("</table>");
    }
}
