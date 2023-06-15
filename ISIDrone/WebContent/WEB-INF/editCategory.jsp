<%-- 
    Document   : editProduct
    Created on : 31 mai 2023, 15 h 53 min 18 s
    Author     : Admin
--%>
<%@page import="entities.Category"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="ISO-8859-1"%>
<%@ page import="util.Const"%>
<jsp:include page="<%=Const.PATH_HEAD_JSP%>"/>
<jsp:include page="<%=Const.PATH_MENU_JSP%>"/>

<%
    Category category = (Category) request.getAttribute("category");
%>

<div class="container" >
    <div class="col-md-6 col-md-offset-3">
        <div class="form-area" >  
            <form role="form" action="categories" method="post" style="margin-bottom: 30px;" id="editForm">
                <h3 style="margin-bottom: 30px; text-align: center;">
                    <%= (category != null) ? "Modifier une category" : "Ajouter une category"%>
                </h3>
                <input type="hidden" name="action" value="categories"/>
                <div class="form-group" >

                    <label for="name">Nom:</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="Ex: Drone " required maxlength="60"
                           value="<%= (category != null) ? category.getName() : ""%>"
                           oninvalid="setCustomValidity('Le Nom est requis et ne doit pas dépasser 60 caractères')"
                           oninput="setCustomValidity('')">
                </div>
                <div class="form-group">
                    <label for="qte">Position:</label>
                    <input type="number" min="<%= (category != null) ? 0 : 1%>" max="99" class="form-control" id="position" name="position" placeholder="Position " required
                           value="<%= (category != null) ? category.getOrder() : 1%>"
                           >
                </div>

                <div class="form-group">
                    <label for="desc">Description</label>
                    <textarea class="form-control" rows="4" id="desc" name="desc" placeholder="Ex: Drone" required><%= (category != null) ? category.getDescription() : ""%></textarea>
                </div>
                <div class="form-group">
                    <label for="statut">Categorie active ou pas:</label>
                    <%if (category != null) {%>

                    <select id="statut" name="statut" class="form-control">
                        <option value="actif" <%= category.getIsActive() ? "selected" : ""%>>ACTIF</option>
                        <option value="inactif" <%= category.getIsActive() ? "" : "selected"%>>INACTIF</option>
                    </select>
                    <input type="hidden" name="id" value="<%=category.getId()%>"/>


                    <%} else {  %>

                    <select id="statut" name="statut" class="form-control">
                        <option value="actif">ACTIF</option>
                        <option value="inactif">INACTIF</option>
                    </select>
                    <%}%>
                   
                </div><!-- comment -->

                <button type="submit" id="submit" class="btn btn-default pull-right" style="margin-top:20px">Envoyer</button>
            </form>
        </div>
    </div>
</div>


<jsp:include page="<%=Const.PATH_FOOTER_JSP%>"/>