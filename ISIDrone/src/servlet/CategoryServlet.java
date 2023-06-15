package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.Const;
import action.ActionCategory;
import entities.Category;
import javax.servlet.http.HttpSession;
import manager.MCategory;
import java.util.ArrayList;
import manager.MItem;
import entities.Item;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;

import manager.MSession;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet(name = "categories", urlPatterns = {"/category", "/categories"})
public class CategoryServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = MSession.getSession(request);
        String action = request.getParameter("action");
        switch (request.getServletPath()) {

            case "/category":

                if (action != null) {
                    String urlRetour = Const.PATH_PAGE_EDIT_CATEGORY;
                    int id = Integer.parseInt(request.getParameter("id"));
                    if (action.equals("new")) {
                        request.getRequestDispatcher(urlRetour).forward(request, response);
                    }

                    if (action.equals("edit")) {
                        
                        ActionCategory.getCategoryById(id, request, response);
                        request.getRequestDispatcher(urlRetour).forward(request, response);
                    }
                    
                    if (action.equals("delete")) {
                        session.setAttribute("category", MCategory.getById(id));
                        ArrayList<Item> items = MItem.getItems(id);
                        session.setAttribute("productSize", items.size());
                        request.getRequestDispatcher(Const.PATH_PAGE_WARNING_DELETE_CATEGORY).forward(request, response);
                    }
                    if (action.equals("deletedConfirmation")) {
                        try {
                            if (ActionCategory.deleteCategoryById(id, request, response) > 0) {
                                request.getRequestDispatcher("categories").forward(request, response);
                            }
                        } catch (SQLIntegrityConstraintViolationException ex) {
                            ex.printStackTrace();
                        }
                    }

                }

                break;
            case "/categories":
               
                action = request.getParameter("action");
                if (action != null) {

                }
                ArrayList<Category> categories = MCategory.getCategories();
                session.setAttribute("categoryList", categories);
                request.getRequestDispatcher(Const.PATH_PAGE_LISTCATEGORY).forward(request, response);

                break;

        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String idCategoryToModify = request.getParameter("id");

        if ("categories".equals(action)) {
            String name = request.getParameter("name");
            String desc = request.getParameter("desc");
            Integer position = Integer.parseInt(request.getParameter("position"));
            String statut = request.getParameter("statut");
            Category category = (idCategoryToModify != null) ? MCategory.getById(Integer.valueOf(idCategoryToModify)) : new Category();

            category.setName(name);
            category.setDescription(desc);
            category.setOrder(position);

            if (statut.equals("actif")) {
                category.setIsActive(true);
            } else {
                category.setIsActive(false);
            }

            if (idCategoryToModify != null) {
                ActionCategory.editCategory(category, category.getId());
            } else {
                MCategory.addNew(category);
            }
            doGet(request, response);
        }

    }

}
