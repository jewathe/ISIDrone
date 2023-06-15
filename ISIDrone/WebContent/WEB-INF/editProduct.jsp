<%-- 
    Document   : editProduct
    Created on : 31 mai 2023, 15 h 53 min 18 s
    Author     : Admin
--%>
<%@page import="entities.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entities.Item"%>

<%@ page import="util.Const"%>
<jsp:include page="<%=Const.PATH_HEAD_JSP%>"/>
<jsp:include page="<%=Const.PATH_MENU_JSP%>"/>

<%
    Item item = (Item) request.getAttribute("item");
    ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("categories");
%>

<div class="container" >
    <div class="col-md-6 col-md-offset-3">
        <div class="form-area" >  
            <form role="form" action="products" method="post" style="margin-bottom: 30px;" id="editForm">
                <h3 style="margin-bottom: 30px; text-align: center;">
                    <%= (item != null) ? "Modifier un produit" : "Ajouter un produit" %>
                </h3>
                <input type="hidden" name="action" value="products"/>
                <div class="form-group" >
                    
                    <label for="name">Nom:</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="Ex: Drone " required maxlength="60"
                           value="<%= (item != null) ? item.getName() : "" %>"
                           oninvalid="setCustomValidity('Le Nom est requis et ne doit pas dépasser 60 caractères')"
                           oninput="setCustomValidity('')"/>
                </div>
                <div class="form-group">
                    <label for="category">Catégorie:</label>
                    <select id="category" name="category" required class="form-control">
                        <%for(Category category : categories){%>
                            <%if(category.getId() != 1){%>
                            <option value="<%=category.getId()%>"
                                    <%if(item != null && item.getCategory() == category.getId()){%>selected<%}%>
                                    >
                                <%=category.getName()%>
                            </option>
                            <%}%>
                        <%}%>
                    </select>
                </div>
                <div class="form-group">
                    <label for="desc">Description</label>
                    <textarea class="form-control" rows="4" id="desc" name="desc" placeholder="Ex: Drone" required><%= (item != null) ? item.getDescription(): "" %></textarea>
                </div>
                <div class="form-group">
                    <label for="price">Prix:</label>
                    <input type="text" class="form-control" id="price" name="price" placeholder="Ex: 500.4" required
                           value="<%= (item != null) ? item.getPrice(): "" %>"
                           pattern="^[1-9]\d*(\.\d+)?$"
                           oninvalid="setCustomValidity('Le Prix est requis et doit etre un nombre')"
                           oninput="setCustomValidity('')"/>
                </div>
                <div class="form-group">
                    <label for="serial">Numero de série:</label>
                    <input type="text" class="form-control" id="serial" name="serial" placeholder="Ex: xxxxxx" required
                           value="<%= (item != null) ? item.getSerial(): "" %>"
                           minlength="10" maxlength="20"
                           pattern="[a-zA-Z]+"
                           oninvalid="setCustomValidity('Le Numero de serie est requis et ne doit contenir que des lettres (majuscules ou minuscules) et comprendre 10 a 20 caratères')"
                           oninput="setCustomValidity('')"/>
                </div>
                <div class="form-group">
                    <label for="qte">Quantité en stock:</label>
                    <input type="number" min="<%= (item != null) ? 0 : 1 %>" max="99" class="form-control" id="qte" name="qte" placeholder="Quantité en stock" required
                           value="<%= (item != null) ? item.getStock(): 1 %>"
                           oninvalid="setCustomValidity('Le Stock st requis et doit etre un nombre entier')"
                           oninput="setCustomValidity('')"/>
                </div>
                <div class="form-group">
                    <label for="img">Image:</label>
                    <input type="text" class="form-control" id="img" name="img" placeholder="Ex: DronePhotographer.jpeg" required readonly
                           value="<%= (item != null) ? item.getImage(): "DronePhotographer.jpeg" %>"/>
                </div>
                
                <%if(item != null){%>
                    <div class="form-group">
                        <label for="statut">Produit actif ou pas:</label>
                        <select id="statut" name="statut" class="form-control">
                            <option value="actif" <%= item.isActive() ? "selected" : "" %>>ACTIF</option>
                            <option value="inactif" <%= item.isActive() ? "" : "selected" %>>INACTIF</option>
                        </select>
                    </div>
                        
                    <input type="hidden" name="id" value="<%=item.getId()%>"/>
                <%}%>
                

                <button type="submit" id="submit" class="btn btn-default pull-right" style="margin-top:20px">Envoyer</button>
            </form>
        </div>
    </div>
</div>


<jsp:include page="<%=Const.PATH_FOOTER_JSP%>"/>