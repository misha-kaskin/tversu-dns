package dao;

import handlers.Configs;
import models.CartItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDao {
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS CART (" +
            "id bigint generated by default as identity primary key," +
            "login varchar(60)," +
            "item_type varchar(60)," +
            "item_id bigint," +
            "item_title varchar(60)," +
            "cost int" +
            ");";

    private static final String FIND_ITEMS_BY_LOGIN = "SELECT * FROM CART WHERE login like ?";
    private static final String DELETE_ITEMS_BY_ID_AND_TYPE = "DELETE FROM CART WHERE item_type like ? AND item_id = ?";
    private static final String DELETE_ITEM_BY_LOGIN_AND_ID = "DELETE FROM CART " +
            "WHERE login like ? AND item_id = ? AND item_type LIKE ?";

    private static final String SAVE = "INSERT INTO CART (login, item_type, item_id, item_title, cost)" +
            "VALUES (?, ?, ?, ?, ?);";

    private static final String DELETE_BY_UD = "DELETE FROM CART WHERE id IN (" +
            "SELECT id FROM CART " +
            "WHERE item_id = ? AND item_type LIKE ? " +
            "LIMIT 1" +
            ")";
    private static final String UPDATE = "UPDATE CART SET item_title =  ?, cost = ? " +
            "WHERE item_id = ? AND item_type LIKE ?";

    private static final String UPDATE_BY_LOGIN = "UPDATE CART SET login = ? WHERE login LIKE ?";

    private static final String DELETE_BY_LOGIN = "DELETE FROM cart WHERE login LIKE ?";

    private static final String EXISTS = "SELECT count(id) " +
            "FROM cart " +
            "WHERE login LIKE ? AND item_type LIKE ? AND item_id = ? AND item_title LIKE ? AND cost = ?";

    public CartDao() throws SQLException {
        Connection conn = DriverManager.getConnection(Configs.DB_URL,
                Configs.DB_USER,
                Configs.DB_PASSWORD);

        Statement st = conn.createStatement();
        st.execute(CREATE_TABLE);

        st.close();
        conn.close();
    }

    public List<CartItem> getItemsByLogin(String login) throws SQLException {
        Connection conn = DriverManager.getConnection(Configs.DB_URL,
                Configs.DB_USER,
                Configs.DB_PASSWORD);

        PreparedStatement ps = conn.prepareStatement(FIND_ITEMS_BY_LOGIN);
        ps.setString(1, login);

        ResultSet rs = ps.executeQuery();

        List<CartItem> cartItems = new ArrayList<>();

        while (rs.next()) {
            CartItem cartItem = CartItem.builder()
                    .itemId(rs.getInt("item_id"))
                    .itemCost(rs.getInt("cost"))
                    .itemTitle(rs.getString("item_title"))
                    .itemType(rs.getString("item_type"))
                    .login(rs.getString("login"))
                    .build();

            cartItems.add(cartItem);
        }

        ps.close();
        conn.close();

        return cartItems;
    }

    public int deleteAllItemsById(String itemType, Integer id) throws SQLException {
        Connection conn = DriverManager.getConnection(Configs.DB_URL,
                Configs.DB_USER,
                Configs.DB_PASSWORD);

        PreparedStatement ps = conn.prepareStatement(DELETE_ITEMS_BY_ID_AND_TYPE);
        ps.setString(1, itemType);
        ps.setInt(2, id);

        int res = ps.executeUpdate();

        ps.close();
        conn.close();

        return res;
    }

    public int deleteItemByLoginId(CartItem cartItem) throws SQLException {
        Connection conn = DriverManager.getConnection(Configs.DB_URL,
                Configs.DB_USER,
                Configs.DB_PASSWORD);

        PreparedStatement ps = conn.prepareStatement(DELETE_ITEM_BY_LOGIN_AND_ID);
        ps.setString(1, cartItem.getLogin());
        ps.setInt(2, cartItem.getItemId());
        ps.setString(3, cartItem.getItemType());

        int res = ps.executeUpdate();

        ps.close();
        conn.close();

        return res;
    }

    public int deleteItemById(CartItem cartItem) throws SQLException {
        Connection conn = DriverManager.getConnection(Configs.DB_URL,
                Configs.DB_USER,
                Configs.DB_PASSWORD);

        PreparedStatement ps = conn.prepareStatement(DELETE_BY_UD);
        ps.setInt(1, cartItem.getItemId());
        ps.setString(2, cartItem.getItemType());

        int res = ps.executeUpdate();

        ps.close();
        conn.close();

        return res;
    }

    public int deleteItemByLogin(String login) throws SQLException {
        Connection conn = DriverManager.getConnection(Configs.DB_URL,
                Configs.DB_USER,
                Configs.DB_PASSWORD);

        PreparedStatement ps = conn.prepareStatement(DELETE_BY_LOGIN);
        ps.setString(1, login);

        int res = ps.executeUpdate();

        ps.close();
        conn.close();

        return res;
    }

    public int save(CartItem cartItem) throws SQLException {
        Connection conn = DriverManager.getConnection(Configs.DB_URL,
                Configs.DB_USER,
                Configs.DB_PASSWORD);

        PreparedStatement ps = conn.prepareStatement(SAVE);
        ps.setString(1, cartItem.getLogin());
        ps.setString(2, cartItem.getItemType());
        ps.setInt(3, cartItem.getItemId());
        ps.setString(4, cartItem.getItemTitle());
        ps.setInt(5, cartItem.getItemCost());

        int res = ps.executeUpdate();

        ps.close();
        conn.close();

        return res;
    }

    public int updateById(CartItem cartItem) throws SQLException {
        Connection conn = DriverManager.getConnection(Configs.DB_URL,
                Configs.DB_USER,
                Configs.DB_PASSWORD);

        PreparedStatement ps = conn.prepareStatement(UPDATE);
        ps.setString(1, cartItem.getItemTitle());
        ps.setInt(2, cartItem.getItemCost());
        ps.setInt(3, cartItem.getItemId());
        ps.setString(4, cartItem.getItemType());

        int res = ps.executeUpdate();

        ps.close();
        conn.close();

        return res;
    }

    public int updateByLogin(String login) throws SQLException {
        Connection conn = DriverManager.getConnection(Configs.DB_URL,
                Configs.DB_USER,
                Configs.DB_PASSWORD);

        PreparedStatement ps = conn.prepareStatement(UPDATE_BY_LOGIN);
        ps.setString(1, login);
        ps.setString(2, login);

        int res = ps.executeUpdate();

        ps.close();
        conn.close();

        return res;
    }

    public boolean exists(CartItem cartItem) throws SQLException {
        Connection conn = DriverManager.getConnection(Configs.DB_URL,
                Configs.DB_USER,
                Configs.DB_PASSWORD);

        PreparedStatement ps = conn.prepareStatement(EXISTS);
        ps.setString(1, cartItem.getLogin());
        ps.setString(2, cartItem.getItemType());
        ps.setInt(3, cartItem.getItemId());
        ps.setString(4, cartItem.getItemTitle());
        ps.setInt(5, cartItem.getItemCost());

        ResultSet rs = ps.executeQuery();

        rs.next();
        int res = rs.getInt(1);

        ps.close();
        conn.close();

        return res > 0;
    }
}
