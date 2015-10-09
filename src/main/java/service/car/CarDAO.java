package service.car;

import entity.Car;

import java.sql.Date;
import java.util.List;

/**
 * Created by Oleg Dashkevych on 09.05.2015.
 */
public interface CarDAO {

    public List<Car> getAll();

    public List<Car> getById(String id);

    public List<Car> getByBrand(String name);

    public List<Car> getByModel(String name);

    public List<Car> getByBrandModel(String brand, String model);

    public List<Car> getByYear(Date date);

    public List<Car> getByClientId(String id);

    public boolean addCar(Car Car);

    public boolean deleteCar(Car Car);

    public boolean updateCar(Car car);

    public Car getUniqueById(String id);

    public boolean checkById(String id);

    public List<Car> getByClientName(String name);
}
