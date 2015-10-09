package service.car;

/**
 * Created by Oleg Dashkevych on 09.05.2015.
 */
public class Factory {

    public  static Factory instance = new Factory();
    public CarDAOImpl carDAOImpl;

    private Factory()
    {

    }

    public static Factory getInstance() {
        return Factory.instance;
    }

    public CarDAOImpl getclientDAO() {
        if(carDAOImpl ==null) carDAOImpl = new CarDAOImpl();

        return carDAOImpl;
    }
}
