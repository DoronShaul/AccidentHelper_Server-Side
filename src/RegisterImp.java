import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterImp {
	String DRIVER = "com.mysql.cj.jdbc.Driver";
	String URL = "jdbc:mysql://localhost:3306/mydatabase?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	String USER = "root";
	String PASSWORD = "root";
	String userQuery = "INSERT into mydatabase.user (email, user_full_name, id ,pass, phone, insurance_company, car_number, car_company, garage) VALUE (?,?,?,?,?,?,?,?,?)"; //inserts the user to the database.
	String supplierQuery = "INSERT into mydatabase.supplier (email, supplier_full_name ,pass, address, capability, open_time, close_time) VALUE (?,?,?,?,?,?,?)"; //inserts the supplier to the database.
	String waitingSupplierQuery = "INSERT into mydatabase.waiting_supplier (email, supplier_full_name ,pass, address, capability, open_time, close_time, contact1_name, contact1_num, contact1_role, contact2_name, contact2_num, contact2_role) VALUE (?,?,?,?,?,?,?,?,?,?,?,?,?)"; //inserts the supplier to the waiting list in the database.
	String addContactQuery = "INSERT into mydatabase.contact (user_email, contact_name ,contact_number, contact_role) VALUE (?,?,?,?)";
	int answer;
	

	/**
	 * this method gets a user info and creates a user in the database.
	 * @param newUser
	 * @return
	 */
	public int registerUser(User newUser) {
		String email = newUser.getEmail();
		String fullName = newUser.getFullName();
		String phone = newUser.getPhone();
		String id = newUser.getId();
		String password = newUser.getPassword();
		String insuranenceCompany = newUser.getInsuranceCompany();
		String carCompany = newUser.getCarCompany();
		String carNum = newUser.getCarNum();
		String garage = newUser.getGarage();
		String contact1Name = newUser.getContact1Name();
		String contact1Num = newUser.getContact1Num();
		String contact2Name = newUser.getContact2Name();
		String contact2Num = newUser.getContact2Num();
		
		try {
			Class.forName(DRIVER);
			java.sql.Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(userQuery);
			PreparedStatement ps1 = connection.prepareStatement(addContactQuery);
			PreparedStatement ps2 = connection.prepareStatement(addContactQuery);

			ps.setString(1, email);
			ps.setString(2, fullName);
			ps.setString(3, id);
			ps.setString(4, password);
			ps.setString(5, phone);
			ps.setString(6, insuranenceCompany);
			ps.setString(7, carNum);
			ps.setString(8, carCompany);
			ps.setString(9, garage);
			
			if(!contact1Name.equals("") && !contact1Num.equals("")) {
				ps1.setString(1, email);
				ps1.setString(2, contact1Name);
				ps1.setString(3, contact1Num);
				ps1.setString(4, null);
				
				ps1.executeUpdate();
				
			}
			
			if(!contact2Name.equals("") && !contact2Num.equals("")) {
				ps2.setString(1, email);
				ps2.setString(2, contact2Name);
				ps2.setString(3, contact2Num);
				ps2.setString(4, null);
				
				ps2.executeUpdate();
				
			}

			answer = ps.executeUpdate();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return answer;
	}
	
	/**
	 * this method gets a supplier info and creates a new supplier in the database.
	 * @param newSupplier
	 * @return
	 */
	public int registerSupplier(Supplier newSupplier) {
		String email = newSupplier.getEmail();
		String fullName = newSupplier.getFullName();
		String address = newSupplier.getAddress();
		String password = newSupplier.getPassword();
		String capability = newSupplier.getCapability();
		String openTime = newSupplier.getOpenTime();
		String closeTime = newSupplier.getCloseTime();
		String contact1Name = newSupplier.getContact1Name();
		String contact1Num = newSupplier.getContact1Num();
		String contact1Role = newSupplier.getContact1Role();
		String contact2Name = newSupplier.getContact2Name();
		String contact2Num = newSupplier.getContact2Num();
		String contact2Role = newSupplier.getContact2Role();
		
		try {
			Class.forName(DRIVER);
			java.sql.Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(supplierQuery);
			PreparedStatement ps1 = connection.prepareStatement(addContactQuery);
			PreparedStatement ps2 = connection.prepareStatement(addContactQuery);

			ps.setString(1, email);
			ps.setString(2, fullName);
			ps.setString(3, password);
			ps.setString(4, address);
			ps.setString(5, capability);
			ps.setString(6, openTime);
			ps.setString(7, closeTime);
			
			if(!contact1Name.equals("") && !contact1Num.equals("")) {
				ps1.setString(1, email);
				ps1.setString(2, contact1Name);
				ps1.setString(3, contact1Num);
				ps1.setString(4, contact1Role);
				
				ps1.executeUpdate();
				
			}
			
			if(!contact2Name.equals("") && !contact2Num.equals("")) {
				ps2.setString(1, email);
				ps2.setString(2, contact2Name);
				ps2.setString(3, contact2Num);
				ps2.setString(4, contact2Role);
				
				ps2.executeUpdate();
				
			}
			
			answer = ps.executeUpdate();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return answer;
	}
	
	/**
	 * this method gets a supplier info and creates a supplier in the waiting list table in the database.
	 * @param newSupplier
	 * @return
	 */
	public int registerWaitingSupplier(Supplier newSupplier) {
		String email = newSupplier.getEmail();
		String fullName = newSupplier.getFullName();
		String address = newSupplier.getAddress();
		String password = newSupplier.getPassword();
		String capability = newSupplier.getCapability();
		String openTime = newSupplier.getOpenTime();
		String closeTime = newSupplier.getCloseTime();
		String contact1Name = newSupplier.getContact1Name();
		String contact1Num = newSupplier.getContact1Num();
		String contact1Role = newSupplier.getContact1Role();
		String contact2Name = newSupplier.getContact2Name();
		String contact2Num = newSupplier.getContact2Num();
		String contact2Role = newSupplier.getContact2Role();

		
		try {
			Class.forName(DRIVER);
			java.sql.Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(waitingSupplierQuery);
			ps.setString(1, email);
			ps.setString(2, fullName);
			ps.setString(3, password);
			ps.setString(4, address);
			ps.setString(5, capability);
			ps.setString(6, closeTime);
			ps.setString(7, openTime);
			ps.setString(8, contact1Name);
			ps.setString(9, contact1Num);
			ps.setString(10, contact1Role);
			ps.setString(11, contact2Name);
			ps.setString(12, contact2Num);
			ps.setString(13, contact2Role);
			
			
			answer = ps.executeUpdate();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return answer;
	}

}
