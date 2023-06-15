<%-- 
    Document   : warningDeleteOrder
    Created on : 9 juin 2023, 13 h 14 min 40 s
    Author     : Admin
--%>
<%@page import="entities.Order"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.Const"%>
<%
    //Format a deux decimal
    Order order = (Order) session.getAttribute("order");
    int infoSize = (Integer) session.getAttribute("infoSize");

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
                    <h3 class="panel-title text-center text-warning"><strong>Suppression de la commande <%= order.getId()%> (<%= infoSize %> lignes de commande)</strong></h3>
                </div>
                <div class="panel-body">
                    <div class="table-responsive text-center">
                        
                        <div>
                            <a class="btn btn-danger" href="order?action=deleteConfirmation&id=<%= order.getId()%>">voulez-vous supprimer?</a>
                            <a class="btn btn-info" href="<%="orders"%>">Retour a la liste des commandes</a>
                        </div>
                        
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<jsp:include page="<%=Const.PATH_FOOTER_JSP%>"/>