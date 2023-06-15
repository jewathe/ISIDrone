package manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entities.Category;
import java.sql.SQLIntegrityConstraintViolationException;

public class MCategory {

    public static ArrayList<Category> getCategories() {
        ArrayList<Category> categories = new ArrayList<Category>();

        try {
            MDB.connect();
            String query = "SELECT * FROM category";
            ResultSet rs = MDB.execQuery(query);
            while (rs.next()) {
                categories.add(new Category(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(5), rs.getInt(4)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MDB.disconnect();
        }

        return categories;
    }

    public static int isExist(int category) {
        int isExist = 0;
        try {
            MDB.connect();
            String query = "SELECT 'exist' FROM category WHERE id = ?";
            PreparedStatement ps = MDB.getPS(query);

            ps.setInt(1, category);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                isExist = 1;
            }
        } catch (SQLException e) {
            isExist = -1;
            e.printStackTrace();
        } finally {
            MDB.disconnect();
        }

        return isExist;
    }

    public static Category getById(int id) {
        Category result = null;

        try {
            MDB.connect();
            String query = "SELECT * FROM category WHERE id = ?";
            PreparedStatement ps = MDB.getPS(query);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next() == true) {

                String name = rs.getString("name");
                String description = rs.getString("description");
                int order = rs.getInt("order");

                Boolean isActive = rs.getBoolean("is_active");
                result = new Category(id, name, description, isActive, order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MDB.disconnect();
        }

        return result;
    }

    public static void addNew(Category category) {
        try {
            MDB.connect();
            String query;

            query = "INSERT INTO category (name, description, `order`, is_active) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = MDB.getPS(query);

            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setInt(3, category.getOrder());
            ps.setBoolean(4, category.getIsActive());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            //      System.err.println(e);
        } finally {
            MDB.disconnect();
        }
    }

    public static void edit(Category item, int id) {
        try {
            MDB.connect();
            String query;

            query = "UPDATE category "
                    + " SET  name = ?, description = ?, `order` = ?, is_active = ?"
                    + " WHERE `id` = ?";
            PreparedStatement ps = MDB.getPS(query);
            ps.setString(1, item.getName());
            ps.setString(2, item.getDescription());
            ps.setDouble(3, item.getOrder());
            ps.setBoolean(4, item.getIsActive());

            ps.setInt(5, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MDB.disconnect();
        }
    }

    private static int getCategoryByName(String name) {
        int result = 0;

        try {
            MDB.connect();
            String query = "SELECT COUNT(*) AS count FROM category WHERE LOWER(`name`) = ?";
            PreparedStatement ps = MDB.getPS(query);
            ps.setString(1, name.toLowerCase());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt("count");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MDB.disconnect();
        }

        return result;
    }

    public static String addnewCategory(Category category) {
        String message = "erreur";

        if (MCategory.getCategoryByName(category.getName()) == 0) {
            try {
                MDB.connect();
                String query = "INSERT INTO `category` (`name`, `description`, `order`) VALUES (?, ?, ?)";
                PreparedStatement ps = MDB.getPS(query);
                ps.setString(1, category.getName());
                ps.setString(2, category.getDescription());
                ps.setShort(3, (short) category.getOrder());

                ps.executeUpdate();

                if (MCategory.newOrder(category.getName(), category.getOrder()).equals("ok")) {
                    message = "La nouvelle categorie a bien été ajoutée avec succes";
                } else {
                    message = "La nouvelle categorie n'a pas ajoutée";
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                MDB.disconnect();
            }
        } else {
            message = "Cette categorie existe deja";
        }

        return message;
    }

    private static String newOrder(String name, int order) {
        String message = "erreur";

        try {
            MDB.connect();
            String query = "UPDATE `category`"
                    + " SET `order` = `order` + 1"
                    + " WHERE `order` >= ? AND !(LOWER(`name`) LIKE ?)";
            PreparedStatement ps = MDB.getPS(query);
            ps.setInt(1, order);
            ps.setString(2, name.toLowerCase());

            ps.executeUpdate();
            message = "order est a jour";

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MDB.disconnect();
        }

        return message;
    }
    
        public static int deleteById(int id) throws SQLIntegrityConstraintViolationException {
        int result = -1;
        String query = "DELETE FROM  category WHERE id=?;";
        try {
            MDB.connect();
            PreparedStatement ps = MDB.getPS(query);
            ps.setInt(1, id);
            result = ps.executeUpdate();

        } catch (SQLException ex) {
            if (result < 0) {
                throw new SQLIntegrityConstraintViolationException("Cette categorie ne peut etre supprime, car il y a encore des produits");
            }
            ex.printStackTrace();
        } finally {
            MDB.disconnect();
        }
        return result;
    }

}
