import admin.*;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException, IOException, NoSuchFieldException, IllegalAccessException, InstantiationException, InterruptedException {
        new EntityManager();

        Connection conn = DriverManager.getConnection(
                Configs.DB_URL,
                Configs.DB_USER,
                Configs.DB_PASSWORD
        );

        Statement st = conn.createStatement();

        //String sql = new String(Files.readAllBytes(Path.of(Configs.DB_SCHEMA)));
        String data = new String(Files.readAllBytes(Path.of(Configs.DB_DATA)));

        //st.execute(sql);
        st.execute(data);

        st.close();
        conn.close();

        Cpu ram = Cpu.builder()
                .manufacture("AMD")
                .socket("AM4")
                .build();

        ItemDao ramDao = new ItemDao(Cpu.class);

        //System.out.println(ramDao.getByConstraint(ram, 0).size());
        //System.out.println(ramDao.getFilters(ram));
        //System.out.println(ramDao.getItemById(1));

        Ram saveRam = Ram.builder()
                .cost(100)
                .model("fasafasfsaf")
                .manufacture("fafasfafafs")
                .typeOfMemory("fsasfafsaf")
                .memoryCapacity(312321)
                .frequency(231D)
                .numberOfModules(1)
                .build();

        //System.out.println(new ItemDao(Ram.class).updateItemById(saveRam,2));

        //System.out.println(ramDao.save(saveRam));

        new UserView().createUserView();

        new AdminView().createAdminWindow();

        User user = User.builder()
                .login("vitek")
                .password("kolyan")
                .build();

        /*UserDao userDao = new UserDao();

        System.out.println(userDao.createUser(user));
        System.out.println(userDao.getUserById(1));

        System.out.println(userDao.updateUserById(User.builder()
                .login("vitek")
                .password("vasyan")
                .build()));

        System.out.println(userDao.getUserById(1));

        System.out.println(userDao.existByLoginPassword(User.builder()
                .login("vitek")
                .password("kolyan")
                .build()));

        System.out.println(userDao.existByLoginPassword(User.builder()
                .login("vitek")
                .password("vasyan")
                .build()));

        System.out.println(userDao.deleteUserById(1));*/
    }
}
