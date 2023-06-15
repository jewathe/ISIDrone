package manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entities.Item;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class MItem {

    public static ArrayList<Item> getItems(int category) {
        ArrayList<Item> items = new ArrayList<Item>();
        try {
            MDB.connect();
            String query;
            PreparedStatement ps;
            ResultSet rs;

            if (category == 1) {
                query = "SELECT * FROM product";
                ps = MDB.getPS(query);
            } else {
                query = "SELECT * FROM product WHERE category = ?";
                ps = MDB.getPS(query);
                ps.setInt(1, category);
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                items.add(getItemFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MDB.disconnect();
        }
        return items;
    }

    public static int deleteById(int id) throws SQLIntegrityConstraintViolationException {
        int result = -1;
        String query = "DELETE FROM  product WHERE id=?;";
        try {
            MDB.connect();
            PreparedStatement ps = MDB.getPS(query);
            ps.setInt(1, id);
            result = ps.executeUpdate();

        } catch (SQLException ex) {
            if (result < 0) {
                throw new SQLIntegrityConstraintViolationException("Ce produit ne peut etre supprime, car il fait l'objet d'une commande");
            }
            ex.printStackTrace();
        } finally {
            MDB.disconnect();
        }
        return result;
    }

    public static Item getItemById(int id) {
        Item item = null;
        try {
            MDB.connect();
            String query = "SELECT * FROM product WHERE id = ?";

            PreparedStatement ps = MDB.getPS(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                item = getItemFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MDB.disconnect();
        }

        return item;
    }

    public static ArrayList<Item> getFeaturedItems() {
        ArrayList<Item> items = new ArrayList<Item>();
        try {
            MDB.connect();
            String query;
            ResultSet rs;

            query = "SELECT * FROM product WHERE id IN (SELECT product FROM featured_product)";

            rs = MDB.execQuery(query);

            while (rs.next()) {
                items.add(getItemFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MDB.disconnect();
        }
        return items;
    }

    public static ArrayList<Item> getItemsBySearch(String search) {
        System.err.println(search);
        ArrayList<Item> items = new ArrayList<>();
        try {
            MDB.connect();
            String query;
            ResultSet rs;

            query = "SELECT * FROM product WHERE LOWER(`name`) LIKE ? OR LOWER(`description`) LIKE ?";
            PreparedStatement ps = MDB.getPS(query);
            ps.setString(1, '%' + (search).toLowerCase() + '%');
            ps.setString(2, '%' + (search).toLowerCase() + '%');
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                //   items = new ArrayList<>();
                while (rs.next()) {
                    items.add(getItemFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MDB.disconnect();
        }
        return items;
    }

    private static Item getItemFromResultSet(ResultSet rs) {

        Item item = new Item();

        try {
            item.setId(rs.getInt("id"));
            item.setCategory(rs.getInt("category"));
            item.setName(rs.getString("name"));
            item.setDescription(rs.getString("description"));
            item.setPrice(rs.getDouble("price"));
            item.setSerial(rs.getString("serialNumber"));
            item.setImage(rs.getString("imgName"));
            item.setStock(rs.getInt("stockQty"));
            item.setActive(rs.getBoolean("isActive"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static int getFeaturedSize(int pdId) {
        int size = 0;
        try {
            MDB.connect();
            String query = "SELECT COUNT(`featured_product`.id) AS size "
                    + "FROM `featured_product` INNER JOIN `product` ON `featured_product`.product = `product`.id WHERE `featured_product`.product = ? GROUP BY `featured_product`.product;";
            PreparedStatement ps = MDB.getPS(query);
            ps.setInt(1, pdId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            size = rs.getInt("size");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MDB.disconnect();
        }

        return size;

    }

    public static void addNewItem(Item item) {
        try {
            MDB.connect();
            String query;
            query = "INSERT INTO product (category, name, description, price, serialNumber, imgName, stockQty)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = MDB.getPS(query);
            ps.setInt(1, item.getCategory());
            ps.setString(2, item.getName());
            ps.setString(3, item.getDescription());
            ps.setDouble(4, item.getPrice());
            ps.setString(5, item.getSerial());
            ps.setString(6, item.getImage());
            ps.setInt(7, item.getStock());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MDB.disconnect();
        }
    }

    public static void editItem(Item item, int id) {
        try {
            MDB.connect();
            String query;

            query = "UPDATE product "
                    + " SET category = ?, name = ?, description = ?, price = ?, serialNumber = ?, imgName = ?, stockQty = ?, isActive = ?"
                    + " WHERE `id` = ?";
            PreparedStatement ps = MDB.getPS(query);
            ps.setInt(1, item.getCategory());
            ps.setString(2, item.getName());
            ps.setString(3, item.getDescription());
            ps.setDouble(4, item.getPrice());
            ps.setString(5, item.getSerial());
            ps.setString(6, item.getImage());

            ps.setInt(7, item.getStock());
            ps.setBoolean(8, item.isActive());
            ps.setInt(9, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MDB.disconnect();
        }
    }
}
