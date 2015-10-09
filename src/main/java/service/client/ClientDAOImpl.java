package service.client;

import entity.Client;
import org.hibernate.Query;
import org.hibernate.Session;
import service.HibernateUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by Oleg Dashkevych on 26.04.2015.
 */
public class ClientDAOImpl implements ClientDAO {

    public List<Client> getAll() {
        Session session = null;
        List<Client> clients = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            clients = session.createQuery("from Client ").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return clients;
    }

    public List<String> getPassportsByName(String name){
        Session session = null;
        List<String > passports = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            passports = session.createQuery("select passportId FROM Client c WHERE name = :name").setParameter("name",name).list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return passports;
    }

    public List<Client> getByPassport(String id) {
        Session session = null;
        List<Client> clients = null;
        if (id != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                clients = session.createQuery("from Client where passportId like :id").setParameter("id", "%" + id + "%").list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            clients = getAll();
        }
        return clients;
    }

    public Client getUniqueByPassport(String id) {
        Session session = null;
        Client client = null;
        if (id != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                client = (Client)session.createQuery("from Client where passportId like :id").setParameter("id", "%" + id + "%").uniqueResult();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            client = null;
        }
        return client;
    }

    public List<Client> getByName(String login) {
        Session session = null;
        List<Client> clients = null;
        if (login != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                clients = session.createQuery("from Client where name like :login").setParameter("login", "%" + login + "%").list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            clients = getAll();
        }
        return clients;
    }

    public List<Client> getByBirth(Date date) {
        Session session = null;
        List<Client> clients = null;
        if (date != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                clients = session.createQuery("from Client where birthday = :date").setParameter("date", date).list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            clients = getAll();
        }
        return clients;
    }

    public boolean addClient(Client client) {
        Session session = null;
        boolean result = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(client);
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
        return result;
    }

    public boolean deleteClient(Client client) {
        Session session = null;
        boolean result = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(client);
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
        Client client = null;
        if (id != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                Query  query = session.createQuery("from Client where passportId = :id").setParameter("id", id);
                client = (Client) query.uniqueResult();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            return false;
        }
        return client!=null;
    }

    public boolean updateClient(Client client) {
        Session session = null;
        boolean result = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(client);
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
        return result;
    }

    public List<Client> getByUniqueName(String name){
        Session session = null;
        List<Client> clients = null;
        if (name != null) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                clients = session.createQuery("from Client where name like :login").setParameter("login", name).list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            clients = null;
        }
        return clients;
    }
}
