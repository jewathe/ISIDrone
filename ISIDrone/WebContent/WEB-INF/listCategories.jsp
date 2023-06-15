<%@page import="manager.MCategory"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="entities.Category"%>
<%@page import="action.ActionCategory"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.Const"%>
<%
    //Format a deux decimal
    DecimalFormat df = new DecimalFormat("####0.00");

    List<Category> categories = (List<Category>) session.getAttribute("categoryList");
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
                    <h3 class="panel-title"><strong>Liste de categories</strong></h3>
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-condensed">
                            <%
                                if (categories.size() != 0) {
                            %>
                            <thead>
                                <tr>
                                    <td><strong>ID</strong></td>

                                    <td class="text-center"><strong>Nom</strong></td>
                                    <td class="text-center"><strong>Ordre d'affichage</strong></td>
                                    <td class="text-right"><strong>Actions</strong></td>
                                </tr>
                            </thead>
                            <%
                            } else {
                            %>
                            <span>Aucune categorie</span>
                            <%
                                }
                            %>
                            <%
                                int i = 0;
                                for (Category category : categories) {
                                    i++;
                            %>

                            <tr>
                                <td ><%= category.getId()%></td>                           
                                <td class="text-center"><%=category.getName()%></td>       
                                <td class="text-center"><%=category.getOrder()%></td>       
                                <td><a  href="category?action=delete&id=<%= category.getId() %>" class="btn  btn-warning " > <span class="glyphicon glyphicon-trash"></span></a></td>

                                <td class="text-center">
                                    <a  href="category?action=edit&id=<%= category.getId()%>" class="btn  btn-secondary " > <span class="glyphicon glyphicon-edit"></span></a>

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