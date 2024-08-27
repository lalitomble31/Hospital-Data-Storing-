package HDS;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patients{
	
	
	//Both Data Coming From Driver Class(Main Class).
	private Connection con;		
	private Scanner sc;			
	
	//Creating Parameterized Class Constructor
	public Patients(Connection con,Scanner sc){
		
		this.con=con;
		this.sc=sc;
	}
	//Creating Methods..
	 public void Add_patients() {
		 
		 //Taking data From User -->
		 System.out.print("Enter Patient Name -->");
		 String name=sc.next();
		 System.out.print("Enter Patient Age -->");
		 int age=sc.nextInt();
		 System.out.println("Enter Patient Gender -->");
		 String Gender=sc.next();
		 
		 //Storing data In database(Establish Connection..)
		 try
		 {
			 String Query="insert into patients(name,age,gender)values(?,?,?)";
			 PreparedStatement ps= con.prepareStatement(Query);
			 ps.setString(1,name);
			 ps.setInt(2,age);
			 ps.setString(3,Gender);
			 int change=ps.executeUpdate();
			 if(change>0){
				 System.out.println("Dataa inserted Succesfully....");
			 }else{
				 System.out.println("Data Not Inserted....");
			 }
			 
		 }catch(SQLException e){
			 e.printStackTrace();
			 
			 } 
	 }
	 
	 public void Show_Patients() {
		 String Query="Select * from Patients";
		 try {
		 PreparedStatement ps=con.prepareStatement(Query);
		 ResultSet rs = ps.executeQuery();
		 System.out.println("Patients-->");
		 while(rs.next()) {
			 int id=rs.getInt("id");
			 String name=rs.getString("name");
			 int age=rs.getInt("Age");
			 String Gender=rs.getString("gender");	
			 
			 System.out.println(id+"   "+name+"   "+age+"   "+Gender);
		 }
		 
		 
		 }catch(SQLException e) {
			 e.printStackTrace();
		 }
		 
	 }

	 public boolean Patient_Avalibility(int id) {
		 String Query="Select * from patients where id=?";
		 try {
			 PreparedStatement ps = con.prepareStatement(Query);
			 ps.setInt(1, id);
			 ResultSet rs=ps.executeQuery();
			 
			 if(rs.next()) {
				 return true;
			 }else {
				 return false;
			 }
		 	}
		 catch(SQLException e) {
			 e.printStackTrace();
		 }
		  return false;
	 }
}