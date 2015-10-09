package service.client;

import entity.Client;

import java.util.List;

/**
 * Created by Oleg Dashkevych on 26.04.2015.
 */
public interface ClientDAO {

    public List<Client> getAll();

    public List<Client> getByPassport(String id);

    public List<Client> getByName(String name);

    public boolean addClient(Client client);

    public boolean deleteClient(Client client);

    public Client getUniqueByPassport(String id);

    public boolean checkByPassport(String id);

    public List<Client> getByUniqueName(String name);

}
