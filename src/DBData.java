import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import javafx.util.Pair;




public class DBData extends HttpServlet {
	DBOperation dbOperation = new DBOperation();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// if the needed operation is to get the suppliers waiting list.
		if (req.getParameter("operation").equals("getWaitingList")) {
			JSONObject jObj = dbOperation.getWaitingList();
			if (jObj != null) {
				PrintWriter pw = resp.getWriter();
				String answer = new String(jObj.toString().getBytes("UTF-8"), "ISO-8859-1");
				pw.write(answer);
				pw.print(answer);
			}
		}
		// if the needed operation is to remove a specific supplier from the waiting
		// list.
		else if (req.getParameter("operation").equals("deleteSupplier")) {
			String email = req.getParameter("email");
			if (dbOperation.removeSupplierFromWaitingList(email) > 0) {
				System.out.println("remove succeed");
				JSONObject status = new JSONObject();
				try {
					status.put("status", "succeed");
					PrintWriter pw = resp.getWriter();
					pw.write(status.toString());
					pw.print(status.toString());

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		// if the needed operation is to add a new accident event.
		else if (req.getParameter("operation").equals("openAccidentEvent")) {
			String email = req.getParameter("email");
			String isPolice = req.getParameter("police");
			String isMda = req.getParameter("mda");
			String isFire = req.getParameter("fire");
			String isContact = req.getParameter("contacts");
			String isInjured = req.getParameter("injured");
			String isRoadBlocked = req.getParameter("roadBlocked");
			String severity = req.getParameter("severity");
			String tow = req.getParameter("tow");
			String wazeLink = req.getParameter("waze");

			AccidentEvent accidentEvent = new AccidentEvent();
			accidentEvent.setEmail(email);
			accidentEvent.setWazeLink(wazeLink);
			accidentEvent.setContacts(Integer.parseInt(isContact));
			accidentEvent.setPolice(Integer.parseInt(isPolice));
			accidentEvent.setMda(Integer.parseInt(isMda));
			accidentEvent.setFire(Integer.parseInt(isFire));
			accidentEvent.setBlockedRoad(Integer.parseInt(isRoadBlocked));
			accidentEvent.setSeverity(Integer.parseInt(severity));
			accidentEvent.setInjured(Integer.parseInt(isInjured));
			accidentEvent.setTow(Integer.parseInt(tow));

			Pair<Integer, Integer> pair = dbOperation.openAccidentEvent(accidentEvent);

			// if the creation of the event in the database succeed.
			if (pair.getValue() > 0) {
				System.out.println("open event succeed");
				JSONObject openEvent = new JSONObject();
				try {
					openEvent.put("id", pair.getKey()); // sends the event id to the client.
					PrintWriter pw = resp.getWriter();
					pw.write(openEvent.toString());
					pw.print(openEvent.toString());

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		}
		// if the needed operation is to get the suppliers list.
		else if (req.getParameter("operation").equals("getSupplierList")) {
			JSONObject jObj = dbOperation.getSupplierList();
			if (jObj != null) {
				PrintWriter pw = resp.getWriter();
				String answer = new String(jObj.toString().getBytes("UTF-8"), "ISO-8859-1");
				pw.write(answer);
				pw.print(answer);
			}
		}
		// if the needed operation is to update the accident event.
		else if (req.getParameter("operation").equals("updateEventStatus")) {
			String id = "" + req.getParameter("event_id");
			String email = req.getParameter("email");

			if (dbOperation.updateAccidentEvent(id, email) > 0) {
				System.out.println("update event succeed");
				JSONObject status = new JSONObject();
				try {
					status.put("status", "succeed");
					PrintWriter pw = resp.getWriter();
					pw.write(status.toString());
					pw.print(status.toString());

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		// if the needed operation is to close the accident event.
		else if (req.getParameter("operation").equals("closeAccidentEvent")) {
			String id = "" + req.getParameter("id");

			if (dbOperation.closeAccidentEvent(id) > 0) {
				System.out.println("close event succeed");
				JSONObject status = new JSONObject();
				try {
					status.put("status", "succeed");
					PrintWriter pw = resp.getWriter();
					pw.write(status.toString());
					pw.print(status.toString());

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		// if the needed operation is to get the admin's last events.
		else if (req.getParameter("operation").equals("getLastEvents")) {
			JSONObject jObj = dbOperation.getLastEvents();
			if (jObj != null) {
				PrintWriter pw = resp.getWriter();
				String answer = new String(jObj.toString().getBytes("UTF-8"), "ISO-8859-1");
				pw.write(answer);
				pw.print(answer);
			}
		}
		// if the needed operation is to get the supplier's last events.
		else if (req.getParameter("operation").equals("getSupplierLastEvents")) {
			String email = req.getParameter("email");
			JSONObject jObj = dbOperation.getSupplierLastEvents(email);
			if (jObj != null) {
				PrintWriter pw = resp.getWriter();
				String answer = new String(jObj.toString().getBytes("UTF-8"), "ISO-8859-1");
				pw.write(answer);
				pw.print(answer);
			}
		}		
		// if the needed operation is to get the admin statistics.
		else if (req.getParameter("operation").equals("getAdminStatistics")) {
			JSONObject jObj = dbOperation.getAdminStatistics();
			if (jObj != null) {
				PrintWriter pw = resp.getWriter();
				String answer = new String(jObj.toString().getBytes("UTF-8"), "ISO-8859-1");
				pw.write(answer);
				pw.print(answer);
			}
		}
		else if (req.getParameter("operation").equals("getSupplierStatistics")) {
			String email = req.getParameter("email");
			JSONObject jObj = dbOperation.getSupplierStatistics(email);
			if (jObj != null) {
				PrintWriter pw = resp.getWriter();
				String answer = new String(jObj.toString().getBytes("UTF-8"), "ISO-8859-1");
				pw.write(answer);
				pw.print(answer);
			}
		}
		// if the needed operation is to get the user name.
		else if (req.getParameter("operation").equals("getUserName")) {
			String email = req.getParameter("email");
			JSONObject jObj = dbOperation.getUserName(email);
			if (jObj != null) {
				PrintWriter pw = resp.getWriter();
				String answer = new String(jObj.toString().getBytes("UTF-8"), "ISO-8859-1");
				pw.write(answer);
				pw.print(answer);
			}
		}
		else if (req.getParameter("operation").equals("getUserContacts")) {
			String email = req.getParameter("email");
			JSONObject jObj = dbOperation.getUserContacts(email);
			if (jObj != null) {
				PrintWriter pw = resp.getWriter();
				String answer = new String(jObj.toString().getBytes("UTF-8"), "ISO-8859-1");
				pw.write(answer);
				pw.print(answer);
			}
		}
		else if (req.getParameter("operation").equals("getUserDetails")) {
			String email = req.getParameter("email");
			JSONObject jObj = dbOperation.getUserDetails(email);
			if (jObj != null) {
				PrintWriter pw = resp.getWriter();
				String answer = new String(jObj.toString().getBytes("UTF-8"), "ISO-8859-1");
				pw.write(answer);
				pw.print(answer);
			}
		}
		// if the needed operation is to get a specific user last events.
		else if (req.getParameter("operation").equals("getUserLastEvents")) {
			String email = req.getParameter("email");
			JSONObject jObj = dbOperation.getUserLastEvents(email);
			if (jObj != null) {
				PrintWriter pw = resp.getWriter();
				String answer = new String(jObj.toString().getBytes("UTF-8"), "ISO-8859-1");
				pw.write(answer);
				pw.print(answer);
			}
		}
		// if the needed operation is to get the supplier name.
		else if (req.getParameter("operation").equals("getSupplierName")) {
			String email = req.getParameter("email");
			JSONObject jObj = dbOperation.getSupplierName(email);
			if (jObj != null) {
				PrintWriter pw = resp.getWriter();
				String answer = new String(jObj.toString().getBytes("UTF-8"), "ISO-8859-1");
				pw.write(answer);
				pw.print(answer);
			}
		}

		else if (req.getParameter("operation").equals("getActiveEventsForSuppliers")) {
			JSONObject jObj = dbOperation.getActiveEventsForSuppliers();
			if (jObj != null) {
				PrintWriter pw = resp.getWriter();
				String answer = new String(jObj.toString().getBytes("UTF-8"), "ISO-8859-1");
				pw.write(answer);
				pw.print(answer);
			}
		}

		else if(req.getParameter("operation").equals("getUserSpecificEvent")) {
			String id = req.getParameter("id");
			JSONObject jObj = dbOperation.getUserSpecificEvent(id);
			if (jObj != null) {
				PrintWriter pw = resp.getWriter();
				String answer = new String(jObj.toString().getBytes("UTF-8"), "ISO-8859-1");
				pw.write(answer);
				pw.print(answer);
			}
		}

		else if(req.getParameter("operation").equals("checkForUser")) {
			String email = req.getParameter("email");
			JSONObject jObj = dbOperation.checkForUser(email);
			if (jObj != null) {
				PrintWriter pw = resp.getWriter();
				String answer = new String(jObj.toString().getBytes("UTF-8"), "ISO-8859-1");
				pw.write(answer);
				pw.print(answer);
			}
		}

		else if(req.getParameter("operation").equals("updateUserPassword")) {
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
			if (dbOperation.updateUserPassword(email, hashedPassword) > 0) {
				System.out.println("update user password succeed");
				JSONObject status = new JSONObject();
				try {
					status.put("status", "succeed");
					PrintWriter pw = resp.getWriter();
					pw.write(status.toString());
					pw.print(status.toString());

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}

		else if(req.getParameter("operation").equals("updateUserDetails")) {
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			String name = req.getParameter("name");
			String phone = req.getParameter("phone");
			String insuranceCompany = req.getParameter("insurance");
			String carCompany = req.getParameter("carCompany");
			String carNum = req.getParameter("carNum");
			String contact1Name = req.getParameter("contact1Name");
			String contact1Num = req.getParameter("contact1Num");
			String contact2Name = req.getParameter("contact2Name");
			String contact2Num = req.getParameter("contact2Num");
			if(req.getParameter("hasPasswordChanged").equals("yes")) {
				String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
				dbOperation.updateUserPassword(email, hashedPassword);
			}
			if(dbOperation.updateUserDetails(email, name, phone, insuranceCompany, carCompany, carNum)>0) {
				//dbOperation.updateUserContacts(email, contact1Name, contact1Num, contact2Name, contact2Num);
				//System.out.println("update user password succeed");
				JSONObject status = new JSONObject();
				try {
					status.put("status", "succeed");
					PrintWriter pw = resp.getWriter();
					pw.write(status.toString());
					pw.print(status.toString());

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		}

	}

}
