<%@page import="entities.User"%>
<%@page import="java.util.HashMap"%>
<%@page import="entities.Order"%>
<%@page import="manager.MCategory"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="entities.Item"%>
<%@page import="action.ActionCategory"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.Const"%>
<%
    //Format a deux decimal
    DecimalFormat df = new DecimalFormat("####0.00");

    // Recupere la liste de commande
    @SuppressWarnings(
            
    
    "unchecked")
        List<Order> orderList = (List<Order>) session.getAttribute("orders");
    HashMap<Integer, User> users = (HashMap<Integer, User>) session.getAttribute("users");
%>

<jsp:include page="<%=Const.PATH_HEAD_JSP%>"/>
<jsp:include page="<%=Const.PATH_MENU_JSP%>"/>
<!-- /.container -->
<!-- Page Content -->
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title text-center"><strong>Liste des commandes</strong></h3>
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-condensed">
                            <%
                                if (orderList.size() != 0) {
                            %>
                            <thead>
                                <tr>

                                    <td class="text-left"><strong>Nom du client</strong></td>
                                    <td class="text-center"><strong>Date de la commande</strong></td>
                                    <td class="text-right"><strong>Actions</strong></td>
                                </tr>
                            </thead>
                            <%
                            } else {
                            %>
                            <span>Aucune commande</span>
                            <%
                                }
                            %>
                            <%
                                for (Order order : orderList) {
                            %>
                            <tr>
                                <td class="text-left"><%= users.get(order.getUserId()).getFirstName()%></td>
                                <td class="text-center"><%= order.getDate()%></td>
                                <td class="text-right">
                                    <%
                                        if (order.getIsShipped()) {
                                    %>
                                    <a  href="order?action=delete&id=<%= order.getId()%>" class="btn  btn-danger " > <span class="glyphicon glyphicon-trash"></span></a>
                                    <%
                                         }
                                    %>
                                    <a href="order?action=toogle&id=<%= order.getId()%>" class= "<%= order.getIsShipped() ? "btn btn-info" : "btn btn-secondary"%>" > <span class= "<%= order.getIsShipped() ? "glyphicon glyphicon-home" : "glyphicon glyphicon-road"%>" ></span></a>
                                </td>
                            </tr>

                            <%
                                }
                            %>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>


<jsp:include page="<%=Const.PATH_FOOTER_JSP%>"/>