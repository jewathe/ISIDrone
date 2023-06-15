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

	List<Item> items = (List<Item>)session.getAttribute("productList");
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
    				<h3 class="panel-title"><strong>Liste de produits</strong></h3>
    			</div>
    			<div class="panel-body">
    				<div class="table-responsive">
    					<table class="table table-condensed">
    						<%
    							if(items.size() != 0){
    						%>
    						<thead>
                                <tr>
        							<td><strong>ID</strong></td>
        							<td class="text-center"><strong>Categorie</strong></td>
        							<td class="text-center"><strong>Nom</strong></td>
        							<td class="text-right"><strong>Prix</strong></td>
                                                                <td class="text-right"><strong>Quantite en stock</strong></td>
                                                                <td class="text-right"><strong>Actions</strong></td>
                                </tr>
    						</thead>
    						<%
    							}else{
    						%>
    							<span>Aucun produit</span>
    						<%
    							}
    						%>
    						<%
    							int i = 0;
    							for(Item item : items){
    								i++;
    						%>
    							
	    							<tr>
	    								<td ><%= item.getId() %></td>
                                                                        <td class="text-center"><%=MCategory.getById(item.getCategory()).getName() %></td>
	    								<td class="text-center"><%=item.getName() %></td>
                                                                        <td class="text-center"><%=item.getPrice() %></td>
                                                                        <td class="text-center"><%=item.getStock() %></td>
                                                                        <td class="text-center">
                                                                            <a  href="products?action=delete&id=<%= item.getId() %>" class="btn  btn-warning " > <span class="glyphicon glyphicon-trash"></span></a>
                                                                            <a  href="products?action=edit&id=<%= item.getId() %>" class="btn  btn-secondary " > <span class="glyphicon glyphicon-edit"></span></a>
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