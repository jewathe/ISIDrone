<%@page import="action.ActionCategory"%>
<%@ page import="entities.User"%>

<%@page import="java.util.ArrayList, entities.Category"%>
<%
    User user = (User) session.getAttribute("user");

    //Vérification si la catégorie sélectionne est valide (Un utilisateur pourrait tenter d'entré une catégorie invalide dans la barre d'adresse)
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

Aucune Catégorie n'est présente pour le moment.
<%
    }
%>