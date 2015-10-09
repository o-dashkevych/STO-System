package service.order1;

/**
 * Created by Oleg Dashkevych on 11.05.2015.
 */
public class Factory {
    public static Factory instance = new Factory();
    public OrderDAOImpl orderDAO;

    private Factory() {
    }

    public static Factory getInstance() {
        return Factory.instance;
    }

    public OrderDAOImpl getordertypeDAO() {
        if (orderDAO == null) orderDAO = new OrderDAOImpl();
        return orderDAO;
    }
}
