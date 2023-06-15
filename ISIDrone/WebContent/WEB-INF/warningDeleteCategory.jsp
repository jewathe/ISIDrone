<%@page import="entities.Category"%>
<%@page import="manager.MCategory"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="entities.Item"%>
<%@page import="action.ActionCategory"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.Const"%>
<%
    //Format a deux decimal
    int productSize = (Integer) session.getAttribute("productSize");
    Category category = (Category) session.getAttribute("category");
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
                    <h3 class="panel-title text-center text-warning"><strong>Suppression de la categorie <%= category.getName()%></strong></h3>
                </div>
                <div class="panel-body">
                    <div class="table-responsive text-center">
                        <%if (productSize > 0) {%>
                        <div>
                            <h5>Impossible de supprimer cette categorie, <%=  productSize%>   sont affectes a cette categorie </h5>
                            <a class="btn btn-info" href="<%="categories"%>">Retour a la liste de categories</a>
                        </div>
                        <%} else {
                        %>
                        <div>
                            <a class="btn btn-danger" href="category?action=deletedConfirmation&id=<%= category.getId()%>">voulez-vous supprimer?</a>
                            <a class="btn btn-info" href="<%="categories"%>">Retour a la liste de categories</a>
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