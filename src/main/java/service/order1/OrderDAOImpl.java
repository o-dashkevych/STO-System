package service.order1;

import entity.Order1;
import org.hibernate.Query;
import org.hibernate.Session;
import service.HibernateUtil;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Created by Oleg Dashkevych on 11.05.2015.
 */
public class OrderDAOImpl implements OrderDAO {

    public List<Order1> getAll() {
        Session session = null;
        List<Order1> order1List = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            order1List = session.createQuery("from Order1").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return order1List;
    }

    public List<Order1> getById(int id) {
        Session session = null;
        List<Order1> order1List = null;
        if (id != 0) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                order1List = session.createQuery("from Order1 where id = :id").setParameter("id", id).list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            order1List = getAll();
        }
        return order1List;
    }

    public List<Order1> getByDate(Date date) {
        Session session = null;
        List<Order1> order1s = null;
        if (date != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                order1s = session.createQuery("from Order1 where dateRequest = :date").setParameter("date", date).list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            order1s = getAll();
        }
        return order1s;
    }

    public List<Order1> getByCarId(String id) {
        Session session = null;
        List<Order1> order1List = null;
        if (!id.equals("")) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                order1List = session.createQuery("from Order1 where carCarId = :id").setParameter("id", id).list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            order1List = getAll();
        }
        return order1List;
    }

    public List<Order1> getByCarName(String name) {
        Session session = null;
        List<Order1> order1List = null;
        if (name != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                order1List = session.createQuery("from Order1 where carCarId = (select carId from Car where carId = :id)").setParameter("id", "%" + name + "%").list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            order1List = getAll();
        }
        return order1List;
    }


    public List<Order1> getByMasterName(String name) {
        Session session = null;
        List<Order1> order1List = null;
        if (name != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                order1List = session.createQuery("from Order1 where masterMasterId = (select passportId from Master where name like :id)").setParameter("id", "%" + name + "%").list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            order1List = getAll();
        }
        return order1List;
    }
 public List<Order1> getByMasterId(String id) {
        Session session = null;
        List<Order1> order1List = null;
        if (id != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                order1List = session.createQuery("from Order1 where masterMasterId like :id").setParameter("id", "%" + id + "%").list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            order1List = getAll();
        }
        return order1List;
    }

    public List<Order1> getByTypeId(int id) {
        Session session = null;
        List<Order1> order1List = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            order1List = session.createQuery("from Order1 where orderTypeId = (select orderId from OrderType where orderId = :id)").setParameter("id", "%" + id + "%").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();

        }
        return order1List;
    }

    public List<Order1> getByTypeName(String name) {
        Session session = null;
        List<Order1> order1List = null;
        if (name != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                order1List = session.createQuery("from Order1 where orderTypeId = (select typeId from OrderType where name like :id)").setParameter("id", "%" + name + "%").list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            order1List = getAll();
        }
        return order1List;
    }

    public List<Order1> getByPrice(double price) {
        Session session = null;
        List<Order1> order1List = null;
        if (price != 0) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                order1List = session.createQuery("from Order1 where price = :id").setParameter("id", price).list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            order1List = getAll();
        }
        return order1List;
    }

    public double minPrice() {
        Session session = null;
        double value = 0.0;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            value = (double) session.createSQLQuery("SELECT min(price) FROM sto.order1").uniqueResult();
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
            value = (double) session.createSQLQuery("SELECT max(price) FROM sto.order1").uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return value;
    }

    public List<Order1> filterPrice(double value) {
        Session session = null;
        List<Order1> order1TypeList = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            order1TypeList = session.createQuery("from Order1 where price >= :value").setParameter("value", value).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return order1TypeList;
    }

    public boolean addOrder(Order1 order1) {
        Session session = null;
        boolean result = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(order1);
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
        return result;
    }

    public boolean deleteOrder(Order1 order1) {
        Session session = null;
        boolean result = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(order1);
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
        return result;
    }

    public Order1 getUniqueById(int id) {
        Session session = null;
        Order1 order1Type = null;
        if (id != 0) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                order1Type = (Order1) session.createQuery("from Order1 where id = :id").setParameter("id", id).uniqueResult();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            order1Type = null;
        }
        return order1Type;
    }

    public boolean checkById(int id) {
        Session session = null;
        Order1 order1 = null;
        if (id != 0) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                Query query = session.createQuery("from Order1 where id = :id").setParameter("id", id);
                order1 = (Order1) query.uniqueResult();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            return false;
        }
        return order1 != null;
    }

    public boolean updateOrder(Order1 order1) {
        Session session = null;
        boolean result = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(order1);
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
        return result;
    }

    public List<Order1> getExec() {
        Session session = null;
        List<Order1> order1List = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            order1List = session.createQuery("from Order1 where executed = 1").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return order1List;
    }

    public List<Order1> getNotExec() {
        Session session = null;
        List<Order1> order1List = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            order1List = session.createQuery("from Order1 where executed = 0").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return order1List;
    }

    public int countFreeOrders() {
        Session session = null;
        BigInteger value = new BigInteger("0");
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            value = (BigInteger) session.createSQLQuery("SELECT count(*) FROM sto.order1 WHERE sto.order1.executed = 0").uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return value.intValue();
    }

    public int getLastId() {
        Session session = null;
        BigInteger rs = BigInteger.valueOf(-1);
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            rs = (BigInteger) session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return rs.intValue();
    }

}
