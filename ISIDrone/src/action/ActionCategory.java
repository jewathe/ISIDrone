package action;

import entities.Category;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entities.Category;
import java.sql.SQLIntegrityConstraintViolationException;
import manager.MCategory;

public class ActionCategory {

    public static void getCategories(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("categories", MCategory.getCategories());
    }

    public static void getCategoryById(int id, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("category", MCategory.getById(id));
    }

    public static int getSelectedCategory(HttpServletRequest request, HttpServletResponse response) {
        //Permet de recevoir la catégorie sélectionné par l'utilisateur
        String paramCategory = request.getParameter("category");

        //ArrayList<Category> categories = MCategory.getCategories();
        int categorySelected;

        if (paramCategory != null) {
            try {
                //Si l'utilisateur entre lui même une valeur pour le paramêtre category dans la barre d'adresse
                // alors s'il la catégorie est invalide, alors la catégorie sélectionné deviendra 1 (qui représente toutes les catégories)
                categorySelected = Integer.valueOf(paramCategory);
                if (MCategory.isExist(categorySelected) == 0) {
                    categorySelected = 1;
                }
            } catch (NumberFormatException e) {
                categorySelected = 1;
            }
        } else {
            categorySelected = 0;
        }

        return categorySelected;
    }

    public static int deleteCategoryById(int id, HttpServletRequest request, HttpServletResponse response) throws SQLIntegrityConstraintViolationException {
        return MCategory.deleteById(id);
    }

    public static void editCategory(Category item, int id) {
        MCategory.edit(item, id);
    }

    public static String addnewCategory(Category category) {
        return MCategory.addnewCategory(category);
    }
}
