package service.order_type;

import entity.OrderType;
import org.hibernate.Query;
import org.hibernate.Session;
import service.HibernateUtil;

import java.util.List;

public class OrderTypeDAOImpl implements OrderTypeDAO {

    public List<OrderType> getAll() {
        Session session = null;
        List<OrderType> orderTypeList = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            orderTypeList = session.createQuery("from OrderType").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return orderTypeList;
    }

    public List<OrderType> getById(int id) {
        Session session = null;
        List<OrderType> orderTypeList = null;
        if (id != 0) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                orderTypeList = session.createQuery("from OrderType where id = :id").setParameter("id", id).list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            orderTypeList = getAll();
        }
        return orderTypeList;
    }

    public List<OrderType> getByName(String name) {
        Session session = null;
        List<OrderType> orderTypeList = null;
        if (name != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                orderTypeList = session.createQuery("from OrderType where name like :name").setParameter("name", "%" + name + "%").list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            orderTypeList = getAll();
        }
        return orderTypeList;
    }

    public OrderType getUniqueByName(String name) {
        Session session = null;
        OrderType orderType = null;
        if (name != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                orderType = (OrderType) session.createQuery("from OrderType where name = :name").setParameter("name", name).uniqueResult();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
            return orderType;
        }
        return orderType;
    }

    public List<OrderType> getByPrice(double price) {
        Session session = null;
        List<OrderType> orderTypeList = null;
        if (price != 0) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                orderTypeList = session.createQuery("from OrderType where price = :price").setParameter("price", price).list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            orderTypeList = getAll();
        }
        return orderTypeList;
    }


    public double minPrice() {
        Session session = null;
        double value = 0.0;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            value = (double) session.createSQLQuery("select min(price) FROM order_type").uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return value;
    }

    public double maxPrice() {
        Session session = null;
        double value = 0.0;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            value = (double) session.createSQLQuery("select max(price) FROM order_type").uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return value;
    }

    public List<OrderType> filterPrice(double value) {
        Session session = null;
        List<OrderType> orderTypeList = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            orderTypeList = session.createQuery("from OrderType where price >= :value").setParameter("value", value).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return orderTypeList;
    }

    public boolean addOrderType(OrderType orderType) {
        Session session = null;
        boolean result = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(orderType);
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
        return result;
    }

    public boolean deleteOrderType(OrderType orderType) {
        Session session = null;
        boolean result = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(orderType);
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
        return result;
    }

    public OrderType getUniqueById(int id) {
        Session session = null;
        OrderType orderType = null;
        if (id != 0) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                orderType = (OrderType) session.createQuery("from OrderType where id = :id").setParameter("id", id).uniqueResult();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            orderType = null;
        }
        return orderType;
    }

    public boolean checkById(int id) {
        Session session = null;
        OrderType orderType = null;
        if (id != 0) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                Query query = session.createQuery("from OrderType where id = :id").setParameter("id", id);
                orderType = (OrderType) query.uniqueResult();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            return false;
        }
        return orderType != null;
    }

    public boolean updateOrderType(OrderType orderType) {
        Session session = null;
        boolean result = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(orderType);
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
        return result;
    }
}
