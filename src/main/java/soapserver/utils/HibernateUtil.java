package soapserver.utils;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import io.github.cdimascio.dotenv.Dotenv;
import soapserver.models.Log;
import soapserver.models.Subscription;

public class HibernateUtil {
    private static final String DB_HOST = Dotenv.load().get("MYSQL_HOST", "user");
    private static final String DB_PORT = Dotenv.load().get("MYSQL_PORT", "3309");
    private static final String DB_NAME = Dotenv.load().get("MYSQL_DATABASE", "kinokuniyawbd");

    private static final String DB_USER = Dotenv.load().get("MYSQL_USER", "user");
    private static final String DB_PASSWORD = Dotenv.load().get("MYSQL_PASSWORD", "pass");
    private static final String DB_URL = String.format("jdbc:mysql://%s:%s/%s", HibernateUtil.DB_HOST, HibernateUtil.DB_PORT, HibernateUtil.DB_NAME);

    private static SessionFactory sessionFactory;

    private static SessionFactory createSessionFactory() {

        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        properties.setProperty("hibernate.connection.url", HibernateUtil.DB_URL);
        properties.setProperty("hibernate.connection.username", HibernateUtil.DB_USER);
        properties.setProperty("hibernate.connection.password", HibernateUtil.DB_PASSWORD);
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.current_session_context_class", "thread");

        Configuration configuration = new Configuration();
        configuration.setProperties(properties);
        configuration.addAnnotatedClass(Subscription.class);
        configuration.addAnnotatedClass(Log.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        if (HibernateUtil.sessionFactory == null) {
            HibernateUtil.sessionFactory = HibernateUtil.createSessionFactory();
        }

        return HibernateUtil.sessionFactory;
    }
}
