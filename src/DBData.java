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

			if (dbOperation.updateAccidentEvent(id) > 0) {
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
		// if the needed operation is to get the last events.
		else if (req.getParameter("operation").equals("getLastEvents")) {
			JSONObject jObj = dbOperation.getLastEvents();
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
			
	}

}
