package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Const;
import action.ActionCart;
import action.ActionCategory;
import action.ActionItems;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Item
 */
@WebServlet(name = "item", urlPatterns = { "/item" ,  "/new-item"})
public class Item extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Item() {
        super();
    }

	/**
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
        @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int item;
		try {
			item = Integer.parseInt(request.getParameter("item"));
		}
		catch(NumberFormatException e) {
			item = -1;
		}
                switch (request.getServletPath()) {
                        case "/new-item":
                             ActionCategory.getCategories(request, response);
                             request.getRequestDispatcher(Const.PATH_PAGE_NEW_ITEM).forward(request, response);
                             break;
                }
		
		ActionItems.getItemById(item, request, response);
		request.getRequestDispatcher(Const.PATH_PAGE_ITEM).forward(request, response);
	}

	/**
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
        @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strId = request.getParameter("itemId");
		String strQty = request.getParameter("qty");
                ActionCategory.getCategories(request, response);
	 
		if(request.getParameter("action").equals("edit")){
                        String errorMessage="";
                        HttpSession session = request.getSession(true);
                        if(session != null){
                              String category = request.getParameter("category");
                              String name = request.getParameter("name");
                              String description = request.getParameter("description");
                              String price = request.getParameter("price");

                        }
                }else{
                        ActionCart.addItem(request, response, strId, strQty);
                }
		doGet(request, response);
	}

}
