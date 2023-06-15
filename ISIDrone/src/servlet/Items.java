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
import java.util.ArrayList;
import manager.MItem;
import entities.Item;
import java.sql.SQLIntegrityConstraintViolationException;
import manager.MOrder;
import manager.MSession;

/**
 * Servlet implementation class Products
 */
@WebServlet(name = "products", urlPatterns = {"/items", "/products"})
public class Items extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Items() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = null;
        String action = request.getParameter("action");

        switch (request.getServletPath()) {

            case "/items":
                ActionCategory.getCategories(request, response);
                String search = request.getParameter("searchInput");

                if (search != null && !search.equals("")) {
                    ActionItems.getItemsBySearch(request, search);
                } else {
                    ActionItems.getItems(request, response);
                }
                request.setAttribute("search", search);
                request.getRequestDispatcher(Const.PATH_PAGE_ITEMS).forward(request, response);
                break;

            case "/products":
                session = MSession.getSession(request);
                action = request.getParameter("action");
                if (action != null) {

                    String urlRetour = Const.PATH_PAGE_EDIT_PRODUCT;

                    if (action.equals("new")) {
                        ActionCategory.getCategories(request, response);
                        request.getRequestDispatcher(urlRetour).forward(request, response);
                    }

                    if (action.equals("edit")) {
                        int id = Integer.parseInt(request.getParameter("id"));
                        ActionItems.getItemById(id, request, response);
                        ActionCategory.getCategories(request, response);
                        request.getRequestDispatcher(urlRetour).forward(request, response);
                    }

                    if (action.equals("delete")) {
                        int id = Integer.parseInt(request.getParameter("id"));
                        session.setAttribute("product", MItem.getItemById(id));
                        session.setAttribute("commandSize", MOrder.getOrdersSizeByProductId(id));
                        session.setAttribute("featuredSize", MItem.getFeaturedSize(id));
                        request.getRequestDispatcher(Const.PATH_PAGE_WARNING_DELETE_PRODUCT).forward(request, response);
                    }
                    if (action.equals("dletedConfirmation")) {
                        int id = Integer.parseInt(request.getParameter("id"));
                        try {
                            if (ActionItems.deleteItemById(id, request, response) > 0) {
                             //  request.getRequestDispatcher(Const.PATH_PAGE_LISTPRODUCT).forward(request, response);
                            }
                        } catch (SQLIntegrityConstraintViolationException ex) {
                            ex.printStackTrace();
                        }
                    }

                }

                ArrayList<Item> items = MItem.getItems(1);

                session.setAttribute("productList", items);
                request.getRequestDispatcher(Const.PATH_PAGE_LISTPRODUCT)
                        .forward(request, response);

                break;

        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String idProductToModify = request.getParameter("id");

        if ("products".equals(action)) {
            String name = request.getParameter("name");
            int category = Integer.parseInt(request.getParameter("category"));
            String desc = request.getParameter("desc");
            double price = Double.parseDouble(request.getParameter("price"));
            String serial = request.getParameter("serial");
            Integer qte = Integer.parseInt(request.getParameter("qte"));
            String img = request.getParameter("img");
            String statut = request.getParameter("statut");
            Item item = (idProductToModify != null) ? MItem.getItemById(Integer.valueOf(idProductToModify)) : new Item();

            item.setName(name);
            item.setCategory(category);
            item.setDescription(desc);
            item.setPrice(price);
            item.setSerial(serial);
            item.setStock(qte);
            item.setImage(img);
            if (statut != null) {
                if (statut.equals("actif")) {
                    item.setActive(true);
                } else {
                    item.setActive(false);
                }
            } else {
                item.setActive(true);
            }
            if (idProductToModify != null) {
                ActionItems.editItem(item, item.getId());
            } else {
                ActionItems.addNewItem(item);
            }

        } else {
            String strId = request.getParameter("itemId");
            String strQty = request.getParameter("qty");
            if (request.getMethod().contains("delete")) {
                System.err.println(strQty);
            }

            ActionCart.addItem(request, response, strId, strQty);

        }
        doGet(request, response);
    }
}
