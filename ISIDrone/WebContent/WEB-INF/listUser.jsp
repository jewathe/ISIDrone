<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="entities.User"%>
<%@page import="manager.MCategory"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="entities.Item"%>
<%@page import="action.ActionCategory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.Const"%>
<%
	//Format a deux decimal
	DecimalFormat df = new DecimalFormat("####0.00");

	HashMap<Integer, User> users = (HashMap<Integer, User>)request.getAttribute("users");
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
    				<h3 class="panel-title"><strong>Liste d'utilisateurs</strong></h3>
    			</div>
    			<div class="panel-body">
    				<div class="table-responsive">
    					<table class="table table-condensed">
    						<%
    							if(users.size() != 0){
    						%>
    						<thead>
                                <tr>
        							<td class="text-center"><strong>Nom</strong></td>
        							<td class="text-right"><strong>Prénom</strong></td>
                                                                <td class="text-right"><strong>Courriel</strong></td>
                                                                <td class="text-right"><strong>Actions</strong></td>
                                </tr>
    						</thead>
    						<%
    							}else{
    						%>
    							<span>Aucun utilisateur</span>
    						<%
    							}
    						%>
    						<%
    							int i = 0;
    							for(User user : users.values()){
                                                        if(user.getUserType().equalsIgnoreCase("CLIENT")){
                                                       
    								i++;
    						%>
    							
	    							<tr>
                                                                        <td class="text-center"><%=user.getFirstName() %></td>
                                                                        <td class="text-center"><%=user.getLastName() %></td>
                                                                        <td class="text-center"><%=user.getEmail() %></td>
                                                                        <td class="text-center">
                                                                            Actions
                                                                        </td>
	    								
	    							</tr>
    							
    						<%	
                                                    }
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