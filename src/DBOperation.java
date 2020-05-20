import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	String addsAccidentEventQuery = "INSERT into mydatabase.accident_event (user_email, involved_details, additional_details,"
			+ " call_police ,call_mda, call_fire, call_contact, call_insurance, call_tow, is_active) VALUE (?,?,?,?,?,?,?,?,?,?)";
	String lastIdQuery = "SELECT LAST_INSERT_ID()";
	String updateEventQuery = "UPDATE mydatabase.accident_event SET involved_details=?, additional_details=?, call_insurance=?, call_tow=? WHERE event_id=?";
	String closeEventQuery = "UPDATE mydatabase.accident_event SET is_active=?, end_time=current_timestamp() WHERE event_id=?";

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
				jObj.put("open_time" + num, rs.getTime(6));
				jObj.put("close_time" + num, rs.getTime(7));
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
			ps.setString(2, accidentEvent.getInvolvedDetails());
			ps.setString(3, accidentEvent.getAdditionalDetails());
			ps.setString(4, "" + accidentEvent.getPolice());
			ps.setString(5, "" + accidentEvent.getMda());
			ps.setString(6, "" + accidentEvent.getFire());
			ps.setString(7, "" + accidentEvent.getContacts());
			ps.setString(8, "" + accidentEvent.getInsurance());
			ps.setString(9, "" + accidentEvent.getTow());
			ps.setString(10, "" + 1);
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

	public int updateAccidentEvent(String id, AccidentEvent accidentEvent) {
		int answer = 0;
		try {
			Class.forName(DRIVER);
			java.sql.Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(updateEventQuery);
			ps.setString(1, accidentEvent.getInvolvedDetails());
			ps.setString(2, accidentEvent.getAdditionalDetails());
			ps.setString(3, ""+accidentEvent.getInsurance());
			ps.setString(4, ""+accidentEvent.getTow());
			ps.setString(5, ""+id);
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
			ps.setString(1, "0");
			ps.setString(2, ""+id);
			answer = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return answer;
	}

}