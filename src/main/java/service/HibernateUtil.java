package service;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private  static SessionFactory sessionFactory;

    private HibernateUtil( ) {}

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
