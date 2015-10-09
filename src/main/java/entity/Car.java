package entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Created by Oleg Dashkevych on 10.05.2015.
 */
@Entity
public class Car {
    private String carId;

    private String model;

    private String brand;

    private Date year;

    @ForeignKey(name = "FK_CLIENT_CARS")
    private String clientPassportId;

    private Client clientByClientPassportId;

    private Collection<Order1> ordersByCarId;

    @Id
    @Column(name = "car_id", nullable = false, insertable = true, updatable = true, length = 20)
    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    @Basic
    @Column(name = "model", nullable = false, insertable = true, updatable = true, length = 45)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "brand", nullable = false, insertable = true, updatable = true, length = 45)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Basic
    @Column(name = "year", nullable = true, insertable = true, updatable = true)
    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    @Basic
    @Column(name = "client_passport_id", nullable = false, insertable = false, updatable = false, length = 18)
    public String getClientPassportId() {
        return clientPassportId;
    }

    public void setClientPassportId(String clientPassportId) {
        this.clientPassportId = clientPassportId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (carId != null ? !carId.equals(car.carId) : car.carId != null) return false;
        if (model != null ? !model.equals(car.model) : car.model != null) return false;
        if (brand != null ? !brand.equals(car.brand) : car.brand != null) return false;
        if (year != null ? !year.equals(car.year) : car.year != null) return false;
        if (clientPassportId != null ? !clientPassportId.equals(car.clientPassportId) : car.clientPassportId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = carId != null ? carId.hashCode() : 0;
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (clientPassportId != null ? clientPassportId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "client_passport_id", referencedColumnName = "passport_id", nullable = false)
    public Client getClientByClientPassportId() {
        return clientByClientPassportId;
    }

    public void setClientByClientPassportId(Client clientByClientPassportId) {
        this.clientByClientPassportId = clientByClientPassportId;
    }

    @OneToMany(mappedBy = "carByCarCarId")
    public Collection<Order1> getOrdersByCarId() {
        return ordersByCarId;
    }

    public void setOrdersByCarId(Collection<Order1> ordersByCarId) {
        this.ordersByCarId = ordersByCarId;
    }
}
