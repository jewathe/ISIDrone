package manager;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Hash;
import entities.Address;
import entities.User;
import java.util.HashMap;
import java.util.Hashtable;

public class MSignUp {

    /* liste des utilisateurs sous forme de HashTable */
    public static HashMap<Integer, User> getUsers() {
        HashMap<Integer, User> result = new HashMap<>();
         try {
            MDB.connect();
            String query;
            ResultSet rs;
            query = "SELECT * FROM user ;";
            rs = MDB.execQuery(query);
            while (rs.next()) {
                result.put(rs.getInt("id"), getUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MDB.disconnect();
        }
        return result ;
    }

    /* -1 : Problème de connexion
	 *  0 : L'adresse email est déjà présente dans la base de données
	 *  1 : L'ajout c'est fait sans problème
	 * */
    public static int signUp(User user) {
        int code = isExist(user);

        if (code == 1) {
            try {
                MDB.connect();

                // Ajoute l'address a la BD
                MSignUp.addAddress(user.getShipAddress());

                String query = "INSERT INTO user (`lastName`, `firstName`, `email`, `password`, `ship_address_id`) VALUES (?, ?, ?, ?, ?)";

                PreparedStatement ps = MDB.getPS(query);

                ps.setString(1, user.getLastName());
                ps.setString(2, user.getFirstName());
                ps.setString(3, user.getEmail());
                ps.setString(4, Hash.SHA1(user.getPassword()));
                ps.setInt(5, user.getShipAddress().getId());

                ps.executeUpdate();
            } catch (SQLException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
                e.printStackTrace();
            } finally {
                MDB.disconnect();
            }
        }
        return code;
    }

    /* -1 : Problème de connexion
	 *  0 : L'adresse email est déjà présente dans la base de données
	 *  1 : L'adresse email n'est pas présente dans la base de données
	 * */
    private static int isExist(User user) {
        int isExist = -1;

        try {
            MDB.connect();

            String query = "SELECT id FROM user WHERE email = ?";
            PreparedStatement ps = MDB.getPS(query);

            ps.setString(1, user.getEmail());
            ResultSet rs = ps.executeQuery();

            isExist = (rs.next() ? 0 : 1);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MDB.disconnect();
        }

        return isExist;
    }

    /* 	0 : Erreur d'insertion
	 *  >0 : Id (cle primaire)
	 * */
    private static int addAddress(Address address) {

        // TODO Faire une transaction
        int id = 0;

        try {
            MDB.connect();
            String query = "INSERT INTO address (`no`, `appt`, `street`, `zip`, `city`, `state`, `country`) VALUES (?, ?, ?, ?, ?, ?, ?)";

            // Genere un prepare statement qui attent l'id creer
            PreparedStatement ps = MDB.getPS(query, 1);

            ps.setString(1, address.getNo());
            ps.setString(2, address.getAppt());
            ps.setString(3, address.getStreet());
            ps.setString(4, address.getZip());
            ps.setString(5, address.getCity());
            ps.setString(6, address.getState());
            ps.setString(7, address.getCountry());

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();

            if (generatedKeys.next()) {
                address.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MDB.disconnect();
        }

        return id;
    }

    private static User getUserFromResultSet(ResultSet rs) {
        User user = new User();
        try {
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setPassword(rs.getString("password"));
            user.setUserType(rs.getString("type_utilisateur"));
            user.setShipAdress(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

}
