package service.client;

public class Factory {
    public  static Factory instance = new Factory();
    public ClientDAOImpl clientDAOImpl;

    private Factory()
    {

    }

    public static Factory getInstance() {
        return Factory.instance;
    }

    public ClientDAOImpl getclientDAO() {
        if(clientDAOImpl ==null) clientDAOImpl = new ClientDAOImpl();

        return clientDAOImpl;
    }
}
