package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

//import javax.security.auth.login.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static String url = "jdbc:mysql://localhost/preproject?serverTimezone=Europe/Moscow&useSSL=false";
    private static String username = "root";
    private static String password = "root";
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private Connection conn;
    private static Util instance;

    private static SessionFactory sessionFactory;

    private Util() {
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
            this.conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println("Connection failed");
            System.out.println(e.getMessage());
        }
    }

    public Connection getConn() {
        return this.conn;
    }

    public static Util getInstance() throws SQLException {
        if (instance == null) {
            instance = new Util();
        } else if (instance.getConn().isClosed()) {
            instance = new Util();
        }
        return instance;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();

            Properties settings = new Properties();

            settings.put(Environment.DRIVER, driver);
            settings.put(Environment.URL, url);
            settings.put(Environment.USER, username);
            settings.put(Environment.PASS, password);
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            settings.put(Environment.HBM2DDL_AUTO, "update");
            configuration.setProperties(settings);
            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            try {
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                StandardServiceRegistryBuilder.destroy(serviceRegistry);
            }

        }
        return sessionFactory;
    }
}
