package service.master;

import entity.Master;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Oleg Dashkevych on 04.05.2015.
 */
public interface MasterDAO {

    public List<Master> getAll();

    public List<Master> getByPassport(String id);

    public List<Master> getByName(String name);

    public List<Master> getBySalary(double salary);

    public List<Master> getByCommission(BigDecimal commission);

    public List<Master> getByCategory(int category);

    public boolean addMaster(Master master);

    public boolean deleteMaster(Master master);

    public Master getUniqueByPassport(String id);

    public boolean checkByPassport(String id);

    public List<Master> filterSalary(double salary);

    public List<Master> filterCommission(BigDecimal commission);

    public double minSalary();

    public double maxSalary();

    public BigDecimal minCommission();

    public BigDecimal maxCommission();

    public int getCount(String id);
}
