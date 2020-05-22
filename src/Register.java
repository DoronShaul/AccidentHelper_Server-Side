import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int answer = 0;
		String type = req.getParameter("type");
		//if the type of the user is a regular user.
		if (type.equals("user")) {
			String fullName = req.getParameter("fullName");
			String email = req.getParameter("email");
			String phone = req.getParameter("phone");
			String id = req.getParameter("id");
			String password = req.getParameter("password");
			String insuranceCompany = req.getParameter("insuranceCompany");
			String carCompany = req.getParameter("carCompany");
			String carNum = req.getParameter("licencePlateNum");
			String garage = req.getParameter("garage");
			String contact1Name = req.getParameter("contact1Name");
			String contact1Num = req.getParameter("contact1Num");
			String contact2Name = req.getParameter("contact2Name");
			String contact2Num = req.getParameter("contact2Num");
			String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

			// creates a new user with its name and password.
			User newUser = new User();
			newUser.setEmail(email);
			newUser.setFullName(fullName);
			newUser.setId(id);
			newUser.setPhone(phone);
			newUser.setPassword(hashedPassword);
			newUser.setCarCompany(carCompany);
			newUser.setCarNum(carNum);
			newUser.setInsuranceCompany(insuranceCompany);
			newUser.setGarage(garage);
			newUser.setContact1Name(contact1Name);
			newUser.setContact1Num(contact1Num);
			newUser.setContact2Name(contact2Name);
			newUser.setContact2Num(contact2Num);

			RegisterImp regImp = new RegisterImp();
			answer = regImp.registerUser(newUser);
		}
		//if the admin approved a supplier registration.
		else if(type.equals("supplier")) {
			String fullName = req.getParameter("supplierName");
			String email = req.getParameter("email");
			String address = req.getParameter("address");
			String password = req.getParameter("password");
			String capability = req.getParameter("capability");
			String openTime = req.getParameter("open_time");
			String closeTime = req.getParameter("close_time");
			String contact1Name = req.getParameter("contact1_name");
			String contact1Num = req.getParameter("contact1_num");
			String contact1Role = req.getParameter("contact1_role");
			String contact2Name = req.getParameter("contact2_name");
			String contact2Num = req.getParameter("contact2_num");
			String contact2Role = req.getParameter("contact2_role");
			//String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

			Supplier newSupplier = new Supplier();
			newSupplier.setEmail(email);
			newSupplier.setFullName(fullName);
			newSupplier.setAddress(address);
			newSupplier.setPassword(password);
			newSupplier.setCapability(capability);
			newSupplier.setOpenTime(openTime);
			newSupplier.setCloseTime(closeTime);
			newSupplier.setContact1Name(contact1Name);
			newSupplier.setContact1Num(contact1Num);
			newSupplier.setContact1Role(contact1Role);
			newSupplier.setContact2Name(contact2Name);
			newSupplier.setContact2Num(contact2Num);
			newSupplier.setContact2Role(contact2Role);

			RegisterImp regImp = new RegisterImp();
			answer = regImp.registerSupplier(newSupplier);
		}
		//if the type of the user is a supplier, register it to the waiting list (admin approval is required). 
		else {
			String fullName = req.getParameter("supplierName");
			String email = req.getParameter("email");
			String address = req.getParameter("address");
			String password = req.getParameter("password");
			String capability = req.getParameter("capability");
			String contact1Name = req.getParameter("contact1Name");
			String contact1Num = req.getParameter("contact1Num");
			String contact1Role = req.getParameter("contact1Role");
			String contact2Name = req.getParameter("contact2Name");
			String contact2Num = req.getParameter("contact2Num");
			String contact2Role = req.getParameter("contact2Role");
			String fromHour = req.getParameter("fromHour");
			String fromMinute = req.getParameter("fromMinute");
			String toHour = req.getParameter("toHour");
			String toMinute = req.getParameter("toMinute");
			String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

			Supplier newSupplier = new Supplier();
			newSupplier.setEmail(email);
			newSupplier.setFullName(fullName);
			newSupplier.setAddress(address);
			newSupplier.setPassword(hashedPassword);
			newSupplier.setCapability(capability);
			newSupplier.setOpenTime(fromHour+":"+fromMinute);
			newSupplier.setCloseTime(toHour+":"+toMinute);
			newSupplier.setContact1Name(contact1Name);
			newSupplier.setContact1Num(contact1Num);
			newSupplier.setContact2Name(contact2Name);
			newSupplier.setContact2Num(contact2Num);
			newSupplier.setContact2Role(contact2Role);
			newSupplier.setContact1Role(contact1Role);

			RegisterImp regImp = new RegisterImp();
			answer = regImp.registerWaitingSupplier(newSupplier);
		}

		

		//if the insertion to the database succeed.
		if (answer > 0) {
			JSONObject registerationStatus = new JSONObject();
			try {
				registerationStatus.put("Register", "succeed");
				PrintWriter pw = resp.getWriter();
				pw.write(registerationStatus.toString());
				pw.print(registerationStatus.toString());
				System.out.println("success");

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

}
