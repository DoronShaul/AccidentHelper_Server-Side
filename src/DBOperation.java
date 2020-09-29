import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.json.JSONObject;

import javafx.util.Pair;


public class DBOperation {
	String DRIVER = "com.mysql.cj.jdbc.Driver";
	String URL = "jdbc:mysql://localhost:3306/mydatabase?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	String USER = "root";
	String PASSWORD = "root";
	String getWaitingListQuery = "SELECT * FROM mydatabase.waiting_supplier";
	String getSupplierListQuery = "SELECT * FROM mydatabase.supplier";
	String removeSupplierFromWaitingListQuery = "DELETE FROM mydatabase.waiting_supplier WHERE email=?";
	String addsAccidentEventQuery = "INSERT into mydatabase.accident_event (user_email, call_police ,call_mda, call_fire, call_contact, "+
			"call_tow, injured, road_blocked, tow_arrived, is_active, waze_link) VALUE (?,?,?,?,?,?,?,?,?,?,?)";
	String lastIdQuery = "SELECT LAST_INSERT_ID()";
	String updateEventQuery = "UPDATE mydatabase.accident_event SET tow_arrived=1, service_provider=? WHERE event_id=?";
	String closeEventQuery = "UPDATE mydatabase.accident_event SET is_active=0, end_time=current_timestamp() WHERE event_id=?";
	String getLastEventsQuery = "SELECT * FROM mydatabase.accident_event";
	String getSupplierLastEventsQuery = "SELECT * FROM mydatabase.accident_event WHERE service_provider=?";
	String getTotalEventsQuery = "SELECT COUNT(*) FROM mydatabase.accident_event";
	String getActiveUsersQuery = "SELECT COUNT(*) FROM mydatabase.user";
	String getTotalSuppliersQuery = "SELECT COUNT(*) FROM mydatabase.supplier";
	String getLastDayEventsQuery = "SELECT COUNT(*) FROM mydatabase.accident_event WHERE start_time BETWEEN CURDATE() AND CURDATE()+1";
	String getLastMonthEventsQuery = "SELECT COUNT(*) FROM mydatabase.accident_event WHERE start_time BETWEEN CURDATE()-30 AND CURDATE()+1";
	String getUserDetailsQuery = "SELECT * FROM mydatabase.user WHERE email=?";
	String getUserLastEventsQuery = "SELECT * FROM mydatabase.accident_event WHERE user_email=?";
	String getUserSpecificEventQuery = "SELECT * FROM mydatabase.accident_event WHERE event_id=?";
	String getSupplierNameQuery = "SELECT * FROM mydatabase.supplier WHERE email=?";
	String getActiveEventsForSuppliersQuery = "SELECT * FROM mydatabase.accident_event WHERE is_active=1 AND tow_arrived=0 AND call_tow=1";


	/**
	 * this method gets all the suppliers on the waiting list, and returns a json
	 * object with their information.
	 * 
	 * @return
	 */
	public JSONObject getWaitingList() {
		try {
			Class.forName(DRIVER);
			java.sql.Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(getWaitingListQuery);
			ResultSet rs = ps.executeQuery();
			JSONObject jObj = new JSONObject();
			int num = 0;
			while (rs.next()) {
				jObj.put("email" + num, rs.getNString(1));
				jObj.put("name" + num, rs.getNString(2));
				jObj.put("pass" + num, rs.getNString(3));
				jObj.put("address" + num, rs.getNString(4));
				jObj.put("capability" + num, rs.getNString(5));
				jObj.put("open_time" + num, rs.getString(6));
				jObj.put("close_time" + num, rs.getString(7));
				jObj.put("contact1_name" + num, rs.getNString(8));
				jObj.put("contact1_num" + num, rs.getNString(9));
				jObj.put("contact1_role" + num, rs.getNString(10));
				jObj.put("contact2_name" + num, rs.getNString(11));
				jObj.put("contact2_num" + num, rs.getNString(12));
				jObj.put("contact2_role" + num, rs.getNString(13));
				num++;
			}
			return jObj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * this method gets all the suppliers on the supplier list, and returns a json
	 * object with their names.
	 * 
	 * @return
	 */
	public JSONObject getSupplierList() {
		try {
			Class.forName(DRIVER);
			java.sql.Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(getSupplierListQuery);
			ResultSet rs = ps.executeQuery();
			JSONObject jObj = new JSONObject();
			int num = 0;
			while (rs.next()) {
				jObj.put("name" + num, rs.getNString(2));
				num++;
			}
			return jObj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * this method gets an email address and remove the specific supplier from the
	 * waiting list in the database.
	 * 
	 * @param email
	 * @return
	 */
	public int removeSupplierFromWaitingList(String email) {
		int answer = 0;
		try {
			Class.forName(DRIVER);
			java.sql.Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(removeSupplierFromWaitingListQuery);
			ps.setString(1, email);
			answer = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return answer;
	}

	/**
	 * this method gets an accident event information and creates an accident event
	 * in the database. it gets the id of the event from the database after the
	 * creation. the method returns a pair of two integers, the first one with the
	 * event id, and the second one with the answer (if the creation of the event
	 * succeed).
	 * 
	 * @param accidentEvent
	 * @return
	 */
	public Pair<Integer, Integer> openAccidentEvent(AccidentEvent accidentEvent) {
		int answer = 0;
		int eventId = 0;
		Pair<Integer, Integer> pair = new Pair<>(0, 0);
		try {
			Class.forName(DRIVER);
			java.sql.Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(addsAccidentEventQuery);
			PreparedStatement ps1 = connection.prepareStatement(lastIdQuery);
			ps.setString(1, accidentEvent.getEmail());
			ps.setString(2, "" + accidentEvent.getPolice());
			ps.setString(3, "" + accidentEvent.getMda());
			ps.setString(4, "" + accidentEvent.getFire());
			ps.setString(5, "" + accidentEvent.getContacts());
			ps.setString(6, "" + accidentEvent.getTow());
			ps.setString(7, "" + accidentEvent.getInjured());
			ps.setString(8, "" + accidentEvent.getBlockedRoad());
			ps.setString(9, "" + 0);
			ps.setString(10, "" + 1);
			ps.setString(11, accidentEvent.getWazeLink());
			answer = ps.executeUpdate();
			ResultSet rs = ps1.executeQuery();
			if (rs.next()) {
				eventId = rs.getInt(1);
			}
			pair = new Pair<>(eventId, answer);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pair;
	}

	public int updateAccidentEvent(String id, String email) {
		int answer = 0;
		try {
			Class.forName(DRIVER);
			java.sql.Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(updateEventQuery);
			ps.setString(1, email);
			ps.setString(2, "" + id);
			answer = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return answer;
	}

	public int closeAccidentEvent(String id) {
		int answer = 0;
		try {
			Class.forName(DRIVER);
			java.sql.Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(closeEventQuery);
			ps.setString(1, id);
			answer = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return answer;
	}

	public JSONObject getLastEvents() {
		try {
			Class.forName(DRIVER);
			java.sql.Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(getLastEventsQuery);
			ResultSet rs = ps.executeQuery();
			JSONObject jObj = new JSONObject();
			int num = 0;
			rs.afterLast();
			while (rs.previous() && num < 3) {
				jObj.put("id" + num, rs.getInt(1));
				jObj.put("email" + num, rs.getNString(2));
				jObj.put("time" + num, rs.getString(12));
				num++;
			}
			return jObj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public JSONObject getSupplierLastEvents(String email) {
		try {
			Class.forName(DRIVER);
			java.sql.Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(getSupplierLastEventsQuery);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			JSONObject jObj = new JSONObject();
			int num = 0;
			rs.afterLast();
			while (rs.previous() && num < 3) {
				jObj.put("id" + num, rs.getInt(1));
				jObj.put("email" + num, rs.getNString(2));
				jObj.put("time" + num, rs.getString(13));
				num++;
			}
			return jObj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public JSONObject getAdminStatistics() {
		try {
			Class.forName(DRIVER);
			java.sql.Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement psActiveUsers = connection.prepareStatement(getActiveUsersQuery);
			PreparedStatement psLastDay = connection.prepareStatement(getLastDayEventsQuery);
			PreparedStatement psTotalEvents = connection.prepareStatement(getTotalEventsQuery);
			PreparedStatement psLastMonth = connection.prepareStatement(getLastMonthEventsQuery);
			PreparedStatement psTotalSuppliers = connection.prepareStatement(getTotalSuppliersQuery);
			int activeUsers, lastDay, lastMonth, totalUsers, totalSuppliers, totalEvents;
			ResultSet rs = psActiveUsers.executeQuery();
			rs.next();
			activeUsers = rs.getInt(1);
			rs = psLastDay.executeQuery();
			rs.next();
			lastDay = rs.getInt(1);
			rs = psTotalEvents.executeQuery();
			rs.next();
			totalEvents = rs.getInt(1);
			rs = psLastMonth.executeQuery();
			rs.next();
			lastMonth = rs.getInt(1);
			rs = psTotalSuppliers.executeQuery();
			rs.next();
			totalSuppliers = rs.getInt(1);
			totalUsers = activeUsers + totalSuppliers;
			
			JSONObject jObj = new JSONObject();
			jObj.put("totalSupplier", totalSuppliers);
			jObj.put("totalEvents", totalEvents);
			jObj.put("totalUsers", totalUsers);
			jObj.put("lastDayEvents", lastDay);
			jObj.put("lastMonthEvents", lastMonth);
			jObj.put("activeUsers", activeUsers);

			return jObj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public JSONObject getUserName(String email) {
		try {
			Class.forName(DRIVER);
			java.sql.Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(getUserDetailsQuery);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			JSONObject jObj = new JSONObject();
			if (rs.next()) {
				jObj.put("userName", rs.getNString(2));
			}
			return jObj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public JSONObject getUserLastEvents(String email) {
		try {
			Class.forName(DRIVER);
			java.sql.Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(getUserLastEventsQuery);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			JSONObject jObj = new JSONObject();
			int num = 0;
			rs.afterLast();
			while (rs.previous() && num < 3) {
				jObj.put("id" + num, rs.getInt(1));
				jObj.put("is_active" + num, rs.getInt(11));
				jObj.put("time" + num, rs.getString(13));
				num++;
			}
			return jObj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public JSONObject getUserSpecificEvent(String id) {
		try {
			Class.forName(DRIVER);
			java.sql.Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(getUserSpecificEventQuery);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			JSONObject jObj = new JSONObject();
			if (rs.next()) {
				jObj.put("is_police", rs.getInt(3));
				jObj.put("is_mda", rs.getInt(4));
				jObj.put("is_fire", rs.getInt(5));
				jObj.put("is_contact", rs.getInt(6));
				jObj.put("is_tow", rs.getInt(7));
				jObj.put("tow_arrived", rs.getInt(10));
				jObj.put("is_active", rs.getInt(11));
				jObj.put("waze", rs.getString(12));
				jObj.put("time", rs.getString(13));
			}
			return jObj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public JSONObject getSupplierName(String email) {
		try {
			Class.forName(DRIVER);
			java.sql.Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(getSupplierNameQuery);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			JSONObject jObj = new JSONObject();
			if (rs.next()) {
				jObj.put("supplierName", rs.getNString(2));
			}
			return jObj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public JSONObject getActiveEventsForSuppliers() {
		try {
			Class.forName(DRIVER);
			java.sql.Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(getActiveEventsForSuppliersQuery);
			ResultSet rs = ps.executeQuery();
			JSONObject jObj = new JSONObject();
			int num = 0;
			while (rs.next()) {
				jObj.put("event_id" + num, rs.getInt(1));	
				jObj.put("waze_link" + num, rs.getNString(12));	
				PreparedStatement ps1 = connection.prepareStatement(getUserDetailsQuery);
				ps1.setString(1, rs.getNString(2));
				ResultSet rs1 = ps1.executeQuery();
				if (rs1.next()) {
					jObj.put("name" + num, rs1.getNString(2));
					jObj.put("phone" + num, rs1.getString(5));
				}
				num++;
			}
			return jObj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
