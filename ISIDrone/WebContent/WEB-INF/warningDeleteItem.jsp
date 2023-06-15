<%@page import="manager.MCategory"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="entities.Item"%>
<%@page import="action.ActionCategory"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.Const"%>
<%
    //Format a deux decimal
    int commandSize = (Integer) session.getAttribute("commandSize");
    int featuredSize = (Integer) session.getAttribute("featuredSize");
    Item item = (Item) session.getAttribute("product");
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
                    <h3 class="panel-title text-center text-warning"><strong>Suppression du produit <%= item.getName()%></strong></h3>
                </div>
                <div class="panel-body">
                    <div class="table-responsive text-center">
                        <%if (commandSize > 0 || featuredSize>0) {%>
                        <div>
                            <h5>Impossible de supprimer ce produits car <%=  commandSize> 0 ? commandSize :  featuredSize %>  <%=  commandSize> 0 ? "commandes" :  "preference" %>  sont affectes a ce produit </h5>
                            <a class="btn btn-info" href="<%="products"%>">Retour a la liste de produits</a>
                        </div>
                        <%} else {
                        %>
                        <div>
                            <a class="btn btn-danger" href="products?action=dletedConfirmation&id=<%=item.getId()%>">voulez-vous supprimer?</a>
                                <a class="btn btn-info" href="<%="products"%>">Retour a la liste de produits</a>
                        </div>
                        <% }
                        %>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<jsp:include page="<%=Const.PATH_FOOTER_JSP%>"/>