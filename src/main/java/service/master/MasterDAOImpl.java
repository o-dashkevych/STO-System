package service.master;

import entity.Master;
import entity.Order1;
import org.hibernate.Query;
import org.hibernate.Session;
import service.HibernateUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Created by Oleg Dashkevych on 04.05.2015.
 */
public class MasterDAOImpl implements MasterDAO{


    public List<Master> getBySalary(double salary){
        Session session = null;
        List<Master> masters = null;
        if (salary != 0) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                masters = session.createQuery("from Master where salary = :salary").setParameter("salary", salary).list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            masters = null;
        }
        return masters;
    }

    public List<Master> getByCommission(BigDecimal commission){
        Session session = null;
        List<Master> masters = null;
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                masters = session.createQuery("from Master where commission = :commission").setParameter("commission", commission).list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        return masters;
    }

    public List<Master> getByCategory(int category){
        Session session = null;
        List<Master> masters = null;
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                masters = session.createQuery("from Master where category = :category").setParameter("category", category).list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        return masters;
    }

    public List<Master> getAll() {
        Session session = null;
        List<Master> masters = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            masters = session.createQuery("from Master").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return masters;
    }
    public List<Order1> getOrdersList(String id) {
        Session session = null;
        List<Order1> masters = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            masters = session.createQuery("select ordersByPassportId from Master where passportId = :id").setParameter("id", id).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return masters;
    }

    public List<Master> getByPassport(String id) {
        Session session = null;
        List<Master> masters = null;
        if (id != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                masters = session.createQuery("from Master where passportId like :id").setParameter("id", "%" + id + "%").list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            masters = getAll();
        }
        return masters;
    }

    public Master getUniqueByPassport(String id) {
        Session session = null;
        Master master = null;
        if (id != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                master = (Master)session.createQuery("from Master where passportId like :id").setParameter("id", id).uniqueResult();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            master = null;
        }
        return master;
    }

    public List<Master> getByName(String login) {
        Session session = null;
        List<Master> masters = null;
        if (login != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                masters = session.createQuery("from Master where name like :login").setParameter("login", "%" + login + "%").list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            masters = getAll();
        }
        return masters;
    }

    public List<Master> getByBirth(Date date) {
        Session session = null;
        List<Master> masters = null;
        if (date != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                masters = session.createQuery("from Master where birthday = :date").setParameter("date", date).list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            masters = getAll();
        }
        return masters;
    }

    public List<Master> getByAcception(Date date) {
        Session session = null;
        List<Master> masters = null;
        if (date != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                masters = session.createQuery("from Master where adoptionDate = :date").setParameter("date", date).list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            masters = getAll();
        }
        return masters;
    }

    public boolean addMaster(Master Master) {
        Session session = null;
        boolean result = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(Master);
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
        return result;
    }

    public boolean deleteMaster(Master Master) {
        Session session = null;
        boolean result = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(Master);
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
        return result;
    }

    public boolean checkByPassport(String id) {
        Session session = null;
        Master master = null;
        if (id != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                Query query = session.createQuery("from Master where passportId = :id").setParameter("id", id);
                master = (Master) query.uniqueResult();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            return false;
        }
        return master!=null;
    }

    public boolean updateMaster(Master Master) {
        Session session = null;
        boolean result = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(Master);
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
        return result;
    }

    public List<Master> filterSalary(double salary){
        Session session = null;
        List<Master> masters = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            masters = session.createQuery("from Master where salary >= :salary").setParameter("salary", salary).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return masters;
    }

    public List<Master> filterCommission(BigDecimal commission){
        Session session = null;
        List<Master> masters = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            masters = session.createQuery("from Master where commission >= :commission").setParameter("commission", commission).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return masters;
    }

    public double minSalary(){
        Session session = null;
        double value = 0.0;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            value = (double)session.createSQLQuery("select min(salary) FROM master").uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return value;
    }

    public double maxSalary(){
        Session session = null;
        double value = 0.0;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            value = (double)session.createSQLQuery("select max(salary) FROM master").uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return value;
    }

    public BigDecimal minCommission(){
        Session session = null;
        BigDecimal value = new BigDecimal(0.0);
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            value = (BigDecimal)session.createSQLQuery("select min(commission) FROM master").uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return value;
    }

    public BigDecimal maxCommission(){
        Session session = null;
        BigDecimal value = new BigDecimal(0.0);
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            value = (BigDecimal)session.createSQLQuery("select max(commission) FROM master").uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return value;
    }


    public int getCount(String id){ // задача автоматизации
        Session session = null;
        BigInteger value = new BigInteger("0");
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            value = (BigInteger)session.createSQLQuery("select count(*) FROM order1 where :id=order1.master_master_id and order1.executed = 0").setParameter("id",id).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return value.intValue();
    }
}
