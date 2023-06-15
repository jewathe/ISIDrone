<%@page import="action.ActionCategory"%>
<%@ page import="entities.User"%>

<%@page import="java.util.ArrayList, entities.Category"%>
<%
    User user = (User) session.getAttribute("user");

    //V�rification si la cat�gorie s�lectionne est valide (Un utilisateur pourrait tenter d'entr� une cat�gorie invalide dans la barre d'adresse)
    @SuppressWarnings(
            
    
    "unchecked")
        ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("categories");
    int categorySelected = ActionCategory.getSelectedCategory(request, response);

    if (categories.size() > 0) {
        for (Category category : categories) {
%>
<% if (user != null && user.getUserType().equals("ADMIN")) {%>

<a href="items?category=<%=category.getId()%>" class="list-group-item<%=(category.getId() == categorySelected ? " active" : "")%>"><%=category.getName()%><%=(category.getIsActive()) ? "" : " <span>(inactive)</span>"%></a>


<%
} else {
    if (category.getIsActive()) {%>
<a href="items?category=<%=category.getId()%>" class="list-group-item<%=(category.getId() == categorySelected ? " active" : "")%>"><%=category.getName()%><%=(category.getIsActive()) ? "" : " <span>(inactive)</span>"%></a>

<%  }
        }
    }

%>  
<%} else {%>

Aucune Cat�gorie n'est pr�sente pour le moment.
<%
    }
%>