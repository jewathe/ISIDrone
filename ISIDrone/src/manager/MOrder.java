package manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Cart;
import entities.Item;
import entities.ItemCart;
import entities.Order;
import entities.User;
import java.sql.SQLIntegrityConstraintViolationException;

public class MOrder {

    public static Order getById(int id) {
        Order order = null;
        try {
            MDB.connect();
            String query = "SELECT * FROM order WHERE id = ?";
            PreparedStatement ps = MDB.getPS(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next() == true) {
                order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setDate(rs.getString("date"));
                order.setIsShipped(rs.getBoolean("is_shipped"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MDB.disconnect();
        }
        return order;
    }

    public static int add(User user, Cart cart) {

        int orderId = 0;

        try {
            // TODO Faire une transaction
            MDB.connect();

            // Partie 1
            // Creer une commande et récupere le ID
            String query = "INSERT INTO `order` (`user_id`, `date`) VALUES (?, now())";

            PreparedStatement ps = MDB.getPS(query, 1);

            ps.setInt(1, user.getId());

            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();

            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            }

            // Partie 2
            // Ajoute tout les items de la commande dans la table order_info
            for (ItemCart itemC : cart.getCart().values()) {

                query = "INSERT INTO `order_info` (`order_id`, `product_id`, `qty`, `price`) VALUES (?, ?, ?, ?)";
                ps = MDB.getPS(query);

                ps.setInt(1, orderId);
                ps.setInt(2, itemC.getId());
                ps.setInt(3, itemC.getQty());
                ps.setDouble(4, itemC.getPrice());

                ps.executeUpdate();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MDB.disconnect();
        }

        return orderId;

    }

    //Number of order_info associated with an order
    public static int getOrderInfosSizeByOrderId(int pdId) {
        int size = 0;
        try {
            MDB.connect();
            String query = "SELECT COUNT(order_id) AS size "
                    + "FROM `order_info`  WHERE order_id = ? GROUP BY order_id;";
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

    //Number of orders associated with a product
    public static int getOrdersSizeByProductId(int pdId) {
        int size = 0;
        try {
            MDB.connect();
            String query = "SELECT COUNT(`order`.id) AS size "
                    + "FROM `order` INNER JOIN `order_info` ON `order`.id = `order_info`.order_id WHERE `order_info`.product_id = ? GROUP BY `order_info`.product_id;";
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

    public static List<Order> getAllOrdersByUserId(int userId) {

        List<Order> orderList = new ArrayList<Order>();

        try {

            MDB.connect();

            String query = "SELECT `order`.id, `order`.date,"
                    + " `order_info`.order_id, `order_info`.product_id, `order_info`.qty, `order_info`.price "
                    + "FROM `order` INNER JOIN `order_info` ON `order`.id = `order_info`.order_id WHERE `order`.user_id = ?;";

            PreparedStatement ps = MDB.getPS(query);

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            int orderId = 0;

            Order order = new Order();

            // Une ligne = un item avec qte
            while (rs.next()) {

                // Si nouvel commande, creer une nouvelle commande
                if (orderId != rs.getInt("id")) {

                    // Ecraser le orderId de condition
                    orderId = rs.getInt("id");

                    // Nouvelle commande
                    order = new Order();

                    order.setId(orderId);
                    order.setUserId(userId);
                    order.setDate(rs.getString("order.date"));

                    // Ajouter la commande a la liste	
                    orderList.add(order);
                }

                // Recupérer l'item suivant
                int itemId = rs.getInt("order_info.product_id");

                // Recuperer l'item complet de la BD
                Item item = MItem.getItemById(itemId);

                // Ecraser le prix et ajouter la quantité
                ItemCart itemC = new ItemCart(item);
                itemC.setQty(rs.getInt("order_info.qty"));
                itemC.setPrice(rs.getDouble("order_info.price"));

                // Ajouter l'itemPanier au panier
                order.addItem(itemC.getSerial(), itemC);
            }

            //orderList.add(order);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MDB.disconnect();
        }

        return orderList;
    }

    public static String deleteOrderById(int id) throws SQLIntegrityConstraintViolationException {
        String result = "ok";
        String query = "DELETE FROM `order`  WHERE id=?  ";
        try {
            MDB.connect();
            PreparedStatement ps = MDB.getPS(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            result = "ok";
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            MDB.disconnect();
        }
        return result;
    }

    public static int deleteForceOrderById(int id) throws SQLIntegrityConstraintViolationException {
        String query = "DELETE FROM `order_info`  WHERE order_id=?  ";
        int result = -1;
        try {
            MDB.connect();
            PreparedStatement ps = MDB.getPS(query);
            ps.setInt(1, id);
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            MDB.disconnect();
        }

        deleteOrderById(id);
        return result;
    }

    public static List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        try {
            MDB.connect();
            String query = "SELECT id,date,user_id, is_shipped FROM `order` ;";
            PreparedStatement ps = MDB.getPS(query);
            ResultSet rs = ps.executeQuery();
            Order order;
            while (rs.next()) {
                // Ecraser le orderId de condition
                order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setDate(rs.getString("date"));
                order.setIsShipped(rs.getBoolean("is_shipped"));
                // Ajouter la commande a la liste	
                orderList.add(order);

            }

            //orderList.add(order);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MDB.disconnect();
        }

        return orderList;

    }

    public static Order getOrderById(int id) {
        Order order = null;
        try {
            MDB.connect();
            String query = "SELECT * FROM `order` WHERE id = ?";

            PreparedStatement ps = MDB.getPS(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                order = getOrderFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MDB.disconnect();
        }

        return order;
    }

    private static Order getOrderFromResultSet(ResultSet rs) {

        Order order = new Order();

        try {
            order.setId(rs.getInt("id"));
            order.setUserId(rs.getInt("user_id"));
            order.setIsShipped(rs.getBoolean("is_shipped"));
            order.setDate(rs.getString("order.date"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public static void updateOrder(Order order, int id) {
        try {

            MDB.connect();
            String query;
            query = "UPDATE `order` "
                    + " SET user_id = ?, is_shipped = ?, date = ?"
                    + " WHERE `id` = ?";
            PreparedStatement ps = MDB.getPS(query);
            ps.setInt(1, order.getUserId());
            ps.setBoolean(2, order.getIsShipped());
            ps.setString(3, order.getDate());
            ps.setInt(4, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MDB.disconnect();
        }
    }
}
