package HDS;
import java.sql.*;
public class Doctors {
	//create private Connection
	private Connection con;
	
	//create the Reference Variable/Connection of Connection...
	Doctors(Connection con){
		this.con=con;
	}
	
	public void show_Doctors() {
		try {
			String Query="Select * From Doctors";
			PreparedStatement ps=con.prepareStatement(Query);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				int id=rs.getInt("Id");
				String Name=rs.getString("Name");
				String Spacialization=rs.getString("Spacialization"); 
				
				System.out.println(id+"   "+Name+"   "+Spacialization);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean Doctor_Avaliblity(int id) {
		
		try {
			String Query="Select * from doctors Where id=?";
			PreparedStatement ps=con.prepareStatement(Query);
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()) {
				return true;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	

	
	
}
