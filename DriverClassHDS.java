package HMS;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DriverClassHDS {
	
	private static final String link="jdbc:mysql://localhost:3306/hospital";
	private static final String Username="root";
	private static final String Password="";
	
	public static void main(String[] args) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		
		
		
		
		try {
			Scanner sc=new Scanner(System.in);
			Connection con=DriverManager.getConnection(link,Username,Password);
			Patients P1=new Patients(con,sc);
			Doctors D1=new Doctors(con);
			
			while(true){
				System.out.println("HOSPITAL MANAGEMENT SYSTEM");
				System.out.println("1) Add_Patients.");
				System.out.println("2) View_Patients.");
				System.out.println("3) View_Doctors.");
				System.out.println("4) Book_Appoinment.");
				System.out.println("5) Exit.");
				System.out.print("Enter Your Choice--> ");
				int choice=sc.nextInt();
				
				switch(choice){
				case 1:
					P1.Add_patients();
					break;
				
				case 2:
					P1.Show_Patients();
					break;
				
				case 3:
					//view_doctors
					D1.show_Doctors();
					break;
					
				case 4:
					//book_appoinment
					Appoinments(con,sc,P1,D1);
					break;
				
				case 5:
					return;
					//exit
					
					
							
				
					
				default:
					System.out.println("Enter Valid Choice");
					break;
					
				}
				
			}

			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		

	}
	
	
	public static void Appoinments(Connection con,Scanner sc,Patients P1,Doctors D1) {
		System.out.print("Enter Patient Id -->" );
		int P_id=sc.nextInt();
		System.out.print("Enter Doctor Id -->" );
		int D_id=sc.nextInt();
		System.out.print("Enter Appoinmet date(YYYY-MM-DD)--> ");
		String A_date=sc.next();		
				
		if(P1.Patient_Avalibility(P_id) && D1.Doctor_Avaliblity(D_id)) //This Method Return True or False Value.
		{
			if(doctor_Present(D_id,A_date,con)) {
				String Query="Insert Into appoinments(P_id,D_id,A_date)Values(?,?,?)";
				try {
				PreparedStatement ps=con.prepareStatement(Query);
				ps.setInt(1,P_id);
				ps.setInt(2,D_id);
				ps.setString(3,A_date);
				int Affect = ps.executeUpdate();
				
				
				if(Affect>0)
				{
					System.out.println("Appointment Booked..");
				}
				else
				{
					System.out.println("Failed....Appointment Not Booked..");
				}
				
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
				
			}else
			{
				System.out.println("Doctor Not Avalible On This Date...");
			}
			
		}
		else
		{
			System.out.println("Doctor Or Patient Not Avalibale In database..");
		}
		
		
	}
	
	public static boolean doctor_Present(int D_id,String A_date,Connection con) {
		String Query="Select count(*) from appoinments where D_id= ? and A_date= ?";
		try {
			PreparedStatement ps = con.prepareStatement(Query);
			ps.setInt(1, D_id);
			ps.setString(2, A_date);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				int count=rs.getInt(1);
				if(count==0) {
					return true;
				}else {
					return false;
				}
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	

}
