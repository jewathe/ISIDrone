<%@page import="util.Const"%>
<%@page import="manager.MLogin"%>
<%@page import="action.ActionLogin"%>
<%@page import="manager.MCookies"%>
<%@ page import="entities.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
//S'il n'y a pas d'utilisateur déjà de connecté
    User user = (User) session.getAttribute("user");

//S'il n'y a pas d'utilisaeur de connecté présentement, on vérifie dans les cookies
// et si nous somme pas en train de nous déconnecter
    if (user == null && request.getAttribute("logout") == null) {
        user = ActionLogin.getUserFromAutoLogin(request);
        session.setAttribute("user", user);
    }

//Si le autoLogin a fonctionné
    if (user != null) {%>
<li id="loginState">
    <a href="#" id="user"><%=user.getFirstName()%></a>
    <ul id="userAction" class="list-unstyled navbar navbar-default">
        <% if (user.getUserType().equals("CLIENT")) {%>
        <li><a href="order-history">Historique commande</a></li>

        <li>&nbsp;</li>
            <% } else {%>
        <li><a href="products?action=new">Nouveau produit</a></li>
        <li><a href="products">Liste de produit</a></li>
        <li><a href="category?action=new">Nouvelle categorie</a></li>
        <li><a href="categories">Liste de categorie</a></li>
        <li><a href="orders">Liste de commandes</a></li>
         <li><a href="users">Liste d'utilisateurs</a></li>
            <% }%>
        <li><a href="login">Déconnexion</a></li>

    </ul>
</li>
<% } else {

%>
<li><a href="signup<%=(request.getParameter("fromCart") != null ? "?fromCart=true" : "")%>">S'enregistrer</a></li>
<li id="loginState"><a href="login">Connexion</a></li>
<%
    }
%>