/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import manager.MCategory;
import util.Const;
import action.ActionCategory;


/**
 *
 * @author Admin
 */
@WebServlet(name = "new-category", urlPatterns = {"/new-category"})
public class NewCategory extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				//request.getRequestDispatcher(Const.PATH_PAGE_NEW_CATEGORY).forward(request, response);

        
    }

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("id"));
        String categoryName = request.getParameter("name");
        String categoryDescription = request.getParameter("description");
        int categoryOrder = Integer.parseInt(request.getParameter("order"));

        // Création d'une instance de Category avec les données de la requête
        //Category category = new Category(categoryId, categoryName, categoryDescription, categoryOrder);

        // Appel à la méthode addnewCategory de la classe ActionCategorie

        // Redirection vers la page de résultat
       // response.sendRedirect(Const.PATH_PAGE_NEW_CATEGORY);
    }
}
