<%-- 
    Document   : newCategory
    Created on : 5 juin 2023, 10 h 27 min 58 s
    Author     : Admin
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="entities.Category"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="util.Const"%>
<jsp:include page="<%=Const.PATH_HEAD_JSP%>"/>
<jsp:include page="<%=Const.PATH_MENU_JSP%>"/>
<!-- /.container -->
<!-- Page Content -->
<div class="container">
    <div class="col-md-5">
        <div class="form-area">  
            <form role="form" action="newCategory" method="post">
                <h3 style="margin-bottom: 25px; text-align: center;">Formulaire de creation d'une categorie</h3>
                <div class="form-group">
                    <input type="text" class="form-control" id="name" name="name" placeholder="Nom" required>
                </div>
               
                <div class="form-group">
                    <textarea  class="form-control" id="description" name="description" placeholder="Description"  rows = "3"  required ></textarea>
                </div>

                
                <div class="form-group">
                    <input type="number" class="form-control" id="order" name="position" placeholder="Position" required>
                </div>
               
                
                
                <div class="form-group btn-group">
                    <input type="checkbox" class="btn-check"  id="state" name="state"  autocomplete="off"/>
                    <label class="btn btn-primary" for="state">Active?</label>
                </div>

                
                <button type="submit" id="submit" class="btn btn-default pull-right">Envoyer</button>
            </form>
        </div>
    </div>

</div>
<jsp:include page="<%=Const.PATH_FOOTER_JSP%>"/>