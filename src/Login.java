import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String SERVER_KEY = "AAAACTwJYU8:APA91bH08fepMQeYFVrC4PSe2"
			+ "_rFi3HzCFKMs76rkyZ0GvRAz__"
			+ "uYhe74FWQoy938WXIzWVS5icylDpQohTc0gVR08kmrchHD0wEBtIwkAhw9NxeldVVpHI8Fk51BoBlj3lFTitEpdSN";
	private static final String TOKEN = "etP3TPIkvms:APA91bFh0Mm5bYsKPBqR46arNcuyxzbt6YCT4Cd4Jfoe1_"
			+ "tJjsA3sr4Ltyll6BLLfCpHw5dkCh-uklO7H88f0ZHCHp5iUxqbPaSmGt2iVlD9dOk4sFXLuqDtaKmc7rJxPb0ra5ox09e4";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		JSONObject loginStatus = new JSONObject();
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		//String token = req.getParameter("token");
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		
		Supplier supplier = new Supplier();
		supplier.setEmail(email);
		supplier.setPassword(password);
		
		LoginImp loginImp = new LoginImp();
		if(loginImp.goToUserLogin(user)) {
			
			try {
				loginStatus.put("type", "user");
				PrintWriter pw = resp.getWriter();
				pw.write(loginStatus.toString());
				pw.print(loginStatus.toString());
				System.out.println("successLogin");
				//FCM.send_FCM_Notification(TOKEN, SERVER_KEY, "hello there!");
				
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		else if(loginImp.goToSupplierLogin(supplier)) {
			
			try {
				loginStatus.put("type", "supplier");
				PrintWriter pw = resp.getWriter();
				pw.write(loginStatus.toString());
				pw.print(loginStatus.toString());
				System.out.println("successLogin");
				//FCM.send_FCM_Notification(TOKEN, SERVER_KEY, "hello there!");
				
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		else {
			try {
				
				loginStatus.put("type", "failed");
				PrintWriter pw = resp.getWriter();
				pw.write(loginStatus.toString());
				pw.print(loginStatus.toString());
				System.out.println("failedLogin");
				
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	

}
