package service.car;

import entity.Car;
import org.hibernate.Query;
import org.hibernate.Session;
import service.HibernateUtil;

import java.sql.Date;
import java.util.List;

/**
 * Created by Oleg Dashkevych on 09.05.2015.
 */
public class CarDAOImpl implements CarDAO{

    public List<Car> getAll(){
        Session session = null;
        List<Car> cars = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            cars = session.createQuery("from Car ").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return cars;
    }

    public List<Car> getByYear(Date date){
        Session session = null;
        List<Car> cars = null;
        if (date != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                cars = session.createQuery("from Car where year = :date").setParameter("date", date).list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            cars = getAll();
        }
        return cars;
    }

    public List<Car> getById(String id){
        Session session = null;
        List<Car> cars = null;
        if (id != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                cars = session.createQuery("from Car where carId like :id").setParameter("id", "%" + id + "%").list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            cars = getAll();
        }
        return cars;
    }

    public List<Car> getByBrand(String name){
        Session session = null;
        List<Car> cars = null;
        if (name != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                cars = session.createQuery("from Car where brand like :name").setParameter("name", "%" + name + "%").list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            cars = getAll();
        }
        return cars;
    }

    public List<Car> getByModel(String name){
        Session session = null;
        List<Car> cars = null;
        if (name != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                cars = session.createQuery("from Car where model like :name").setParameter("name", "%" + name + "%").list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            cars = getAll();
        }
        return cars;
    }

    public List<Car> getByBrandModel(String brand, String model){
        Session session = null;
        List<Car> cars = null;
        if (brand != "" || model != "") {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                cars = session.createQuery("from Car where brand = :brand and model = :model").setParameter("brand", brand).setParameter("model", model).list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        }
        return cars;
    }


    public List<Car> getByYear(java.util.Date date){
        Session session = null;
        List<Car> cars = null;
        if (date != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                cars = session.createQuery("from Car where year = :date").setParameter("date", date).list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            cars = getAll();
        }
        return cars;
    }

    public boolean addCar(Car car){
        Session session = null;
        boolean result = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(car);
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
        return result;
    }

    public boolean deleteCar(Car car){
        Session session = null;
        boolean result = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(car);
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
        return result;
    }

    public boolean updateCar(Car car) {
        Session session = null;
        boolean result = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(car);
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
        return result;
    }

    public Car getUniqueById(String id){
        Session session = null;
        Car car = null;
        if (id != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                car = (Car)session.createQuery("from Car where carId like :id").setParameter("id", "%" + id + "%").uniqueResult();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            car = null;
        }
        return car;
    }

    public boolean checkById(String id){
        Session session = null;
        Car car = null;
        if (id != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                Query query = session.createQuery("from Car where carId = :id").setParameter("id", id);
                car = (Car) query.uniqueResult();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            return false;
        }
        return car!=null;
    }


    public List<Car> getByClientId(String id){
        Session session = null;
        List<Car> cars = null;
        if (id != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                cars = session.createQuery("from Car where clientPassportId like :id").setParameter("id","%" + id + "%").list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            cars = getAll();
        }
        return cars;
    }

    public List<Car> getByClientName(String name){
        Session session = null;
        List<Car> cars = null;
        if (name != "") {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                cars = session.createQuery("from Car where clientPassportId = (select passportId from Client where name like :name)").setString("name","%" + name + "%").list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
                if(cars == null){
                    cars = getAll();
                }
            }
        } else {
            cars = getAll();
        }
        return cars;
    }
}
