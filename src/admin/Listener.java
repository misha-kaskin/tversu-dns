package admin;

import java.sql.SQLException;

public interface Listener {
    void update() throws SQLException, NoSuchFieldException, IllegalAccessException;
}
