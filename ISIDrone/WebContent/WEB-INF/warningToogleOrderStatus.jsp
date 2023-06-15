<%@page import="entities.Order"%>
<%@page import="manager.MCategory"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="entities.Item"%>
<%@page import="action.ActionCategory"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.Const"%>
<%
    //Format a deux decimal

    Order order = (Order) session.getAttribute("order");
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
                    <h3 class="panel-title text-center text-warning"><strong>Statut d'envoie de la commande <%= order.getId()%></strong></h3>
                </div>
                <div class="panel-body">
                    <div class="table-responsive text-center">
                        Voulez-vous <%= order.getIsShipped() ? "signaler" : "stopper"%> l'envoie de la commande <%= order.getId()%> ? 
                        <div>
                            <a class="btn btn-warning" href="order?action=confirmation&id=<%=order.getId()%>">OUI </a>
                            <a class="btn btn-info" href="<%="orders"%>">NON</a>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<jsp:include page="<%=Const.PATH_FOOTER_JSP%>"/>