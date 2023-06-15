package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.Const;
import action.ActionSignUp;

@WebServlet(name = "users", urlPatterns = { "/users" })
public class ServletUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ServletUser() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    ActionSignUp.getUsers(0,request, response);	
            request.getRequestDispatcher(Const.PATH_PAGE_LISTUSER).forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		

}
}
