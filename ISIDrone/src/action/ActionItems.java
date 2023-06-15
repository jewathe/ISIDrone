package action;

import java.sql.SQLIntegrityConstraintViolationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entities.Item;

import manager.MItem;

public class ActionItems {
	
	public static void getItems(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("items", MItem.getItems(ActionCategory.getSelectedCategory(request, response)));
	}

	public static void getItemById(int id, HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("item", MItem.getItemById(id));
	}
        
        public static int deleteItemById(int id, HttpServletRequest request, HttpServletResponse response) throws SQLIntegrityConstraintViolationException{
                return MItem.deleteById(id);
        }

        
        public static void getItemsBySearch(HttpServletRequest request, String search) {
            
		request.setAttribute("items", MItem.getItemsBySearch(search));
	}
        public static void addNewItem(Item item){
            MItem.addNewItem(item);
        }
        
        public static void editItem(Item item, int idProductToModify){
            MItem.editItem(item, idProductToModify);
        }
	
}
