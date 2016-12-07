package org.dbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class dbcontrol {
	Connection conn;
	 private String[] fields={"座號","姓名","地址","hobby","passwd"};
     public dbcontrol(){
    	 try{
    		 Class.forName("com.mysql.jdbc.Driver");
    		 Properties prop=new Properties();
    		 prop.setProperty("user","root");
    		 prop.setProperty("password", "root");
    		 conn=DriverManager.getConnection("jdbc:mysql://localhost/school?&characterEncoding=Utf-8",prop);
    		 System.out.println("Connection OK");
    	 }
    	 catch(Exception e){
    		 System.out.println(e.toString());
    	 }
     }
     //從school這個資料庫中撈出資料表student中所有的資料
	public ArrayList<HashMap<String,String>> queryData(){
		ArrayList<HashMap<String,String>> data=new ArrayList<>();
		try{
			PreparedStatement pstmt=conn.prepareStatement("select * from 學生");
		    ResultSet rs=pstmt.executeQuery();
		    while(rs.next()){
		    	HashMap<String,String> row=new HashMap();
		    	for(String field:fields){
		    		row.put(field,rs.getString(field));
		    	}
	           data.add(row);   	
		    }
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		return data;
	}
	
	//新增資料到資料表中
	public void addData(String 姓名,String 地址,String hobby,String passwd ){
		try{
			PreparedStatement pstmt=conn.prepareStatement(
					"insert into 學生(姓名,地址,hobby,passwd) "+ " values(?,?,?,?)");
		    pstmt.setString(1, 姓名);
		    pstmt.setString(2, 地址);
		    pstmt.setString(3, hobby);
		    pstmt.setString(4, passwd);
		    pstmt.execute();
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	//從資料表中刪除資料
	public void deleteData(String 座號){
		try{
			PreparedStatement pstmt=conn.prepareStatement(
					"delete from 學生 where 座號=?");
			pstmt.setString(1, 座號);
			pstmt.execute();
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	//修改資料內容
	public void editData(String updateid,String 姓名,String 地址,String hobby,String passwd ){
		try{
			PreparedStatement pstmt=conn.prepareStatement(
					"update 學生 set 姓名=?, 地址=? , hobby=?,passwd=? where 座號=?");
			pstmt.setString(1, 姓名);
			pstmt.setString(2, 地址);
			pstmt.setString(3, hobby);
			pstmt.setString(4,passwd);
			pstmt.setString(5,updateid);
			pstmt.execute();
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}
}
