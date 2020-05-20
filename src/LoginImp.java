import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginImp {
	String DRIVER = "com.mysql.cj.jdbc.Driver";
	String URL = "jdbc:mysql://localhost:3306/mydatabase?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	String USER = "root";
	String PASSWORD = "root";
	String userQuery = "SELECT * from mydatabase.user WHERE email=?";
	String supplierQuery = "SELECT * from mydatabase.supplier WHERE email=?";
	int answer;
	
	
	
	public boolean goToUserLogin(User user) {
		try {
			Class.forName(DRIVER);
			java.sql.Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(userQuery);
			ps.setString(1, user.getEmail());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				
				String hashedPass = rs.getString(4);
				if(BCrypt.checkpw(user.getPassword(), hashedPass)) {
					return true;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
		
		
	}



	public boolean goToSupplierLogin(Supplier supplier) {
		try {
			Class.forName(DRIVER);
			java.sql.Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(supplierQuery);
			ps.setString(1, supplier.getEmail());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				
				String hashedPass = rs.getString(3);
				if(BCrypt.checkpw(supplier.getPassword(), hashedPass)) {
					return true;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	
	
	
}
