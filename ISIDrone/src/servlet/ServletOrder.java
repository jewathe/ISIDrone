package servlet;

import action.ActionOrder;
import entities.Order;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import manager.MOrder;
import manager.MSession;
import manager.MSignUp;

import util.Const;

/**
 * Servlet implementation class Order
 */
@WebServlet(name = "order", urlPatterns = {"/order", "/orders"})
public class ServletOrder extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = MSession.getSession(request);

        switch (request.getServletPath()) {
            case "/orders":
                session = MSession.getSession(request);
                session.setAttribute("orders", MOrder.getAllOrders());
                session.setAttribute("users", MSignUp.getUsers());
                request.getRequestDispatcher(Const.PATH_PAGE_INVOICE_LIST).forward(request, response);
                break;
            case "/order":
                int id = Integer.parseInt(request.getParameter("id"));
                session.setAttribute("order", MOrder.getOrderById(id));
                String action = request.getParameter("action");
                if (action.equals("confirmation")) {
                    Order order = MOrder.getOrderById(id);
                    order.toogleIsShipped();
                    ActionOrder.updateOrder(order, id);
                    session.setAttribute("orders", MOrder.getAllOrders());
                    request.getRequestDispatcher(Const.PATH_PAGE_INVOICE_LIST).forward(request, response);
                }
                if (action.equals("toogle")) {
                    ActionOrder.getOrderById(id, request, response);
                    request.getRequestDispatcher(Const.PATH_PAGE_WARNING_TOOGLE_ORDER).forward(request, response);
                }
                if (action.equals("delete")) {

                    session.setAttribute("infoSize", MOrder.getOrderInfosSizeByOrderId(id));
                    request.getRequestDispatcher(Const.PATH_PAGE_WARNING_DELETE_ORDER).forward(request, response);
                }

                if (action.equals("deleteConfirmation")) {
                    try {

                        if (ActionOrder.deleteById(id, request, response) > 0) {

                        }
                    } catch (SQLIntegrityConstraintViolationException ex) {
                        ex.printStackTrace();
                    }
                }
                List<Order> items = MOrder.getAllOrders();

                session.setAttribute("orders", items);
                request.getRequestDispatcher(Const.PATH_PAGE_INVOICE_LIST)
                        .forward(request, response);
                break;

        }
        //  request.getRequestDispatcher(Const.PATH_PAGE_INVOICE_LIST).forward(request, response);
    }

}
