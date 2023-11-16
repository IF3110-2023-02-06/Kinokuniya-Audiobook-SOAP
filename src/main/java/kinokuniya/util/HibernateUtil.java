package kinokuniya.util;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import io.github.cdimascio.dotenv.Dotenv;
import kinokuniya.model.Logging;
import kinokuniya.model.Subscription;

public class HibernateUtil {
    private static final String DB_HOST = Dotenv.load().get("MYSQL_HOST", "localhost");
    private static final String DB_PORT = Dotenv.load().get("MYSQL_PORT", "3306");
    private static final String DB_NAME = Dotenv.load().get("MYSQL_DATABASE", "kinokuniya_soap");
    private static final String DB_USER = Dotenv.load().get("MYSQL_USER", "root");
    private static final String DB_PASS = Dotenv.load().get("MYSQL_PASSWORD", "password");

    private static final String DB_URL = String.format("jdbc:mysql://%s:%s/%s", DB_HOST, DB_PORT, DB_NAME);

    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        properties.setProperty("hibernate.connection.url", HibernateUtil.DB_URL);
        properties.setProperty("hibernate.connection.username", HibernateUtil.DB_USER);
        properties.setProperty("hibernate.connection.password", HibernateUtil.DB_PASS);
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.current_session_context_class", "thread");

        Configuration configuration = new Configuration();
        configuration.setProperties(properties);
        configuration.addAnnotatedClass(Logging.class);
        configuration.addAnnotatedClass(Subscription.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        if (HibernateUtil.sessionFactory == null) {
            HibernateUtil.sessionFactory = HibernateUtil.buildSessionFactory();
        }
        return HibernateUtil.sessionFactory;
    }
}
