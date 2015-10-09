package service.master;

/**
 * Created by Oleg Dashkevych on 04.05.2015.
 */
public class Factory {
    public  static Factory instance = new Factory();
    public MasterDAOImpl MasterDAOImpl;

    private Factory()
    {
    }

    public static Factory getInstance() {
        return Factory.instance;
    }

    public MasterDAOImpl getclientDAO() {
        if(MasterDAOImpl ==null) MasterDAOImpl = new MasterDAOImpl();

        return MasterDAOImpl;
    }
}
