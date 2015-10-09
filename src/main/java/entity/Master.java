package entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;

/**
 * Created by Oleg Dashkevych on 10.05.2015.
 */
@Entity
public class Master {
    private String passportId;

    private String name;

    private Date birthday;

    private Date adoptionDate;

    @ForeignKey(name = "FK_TYPE_MASTERS")
    private int category;

    private BigDecimal commission;

    private double salary;

    private OrderType orderTypeByCategory;

    private Collection<Order1> ordersByPassportId;

    @Id
    @Column(name = "passport_id", nullable = false, insertable = true, updatable = true, length = 18)
    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    @Basic
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 90)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "birthday", nullable = false, insertable = true, updatable = true)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "adoption_date", nullable = true, insertable = true, updatable = true)
    public Date getAdoptionDate() {
        return adoptionDate;
    }

    public void setAdoptionDate(Date adoptionDate) {
        this.adoptionDate = adoptionDate;
    }

    @Basic
    @Column(name = "category", nullable = false, insertable = false, updatable = false)
    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Basic
    @Column(name = "commission", nullable = false, insertable = true, updatable = true, precision = 2)
    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    @Basic
    @Column(name = "salary", nullable = false, insertable = true, updatable = true, precision = 0)
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Master master = (Master) o;

        if (category != master.category) return false;
        if (Double.compare(master.salary, salary) != 0) return false;
        if (passportId != null ? !passportId.equals(master.passportId) : master.passportId != null) return false;
        if (name != null ? !name.equals(master.name) : master.name != null) return false;
        if (birthday != null ? !birthday.equals(master.birthday) : master.birthday != null) return false;
        if (adoptionDate != null ? !adoptionDate.equals(master.adoptionDate) : master.adoptionDate != null)
            return false;
        if (commission != null ? !commission.equals(master.commission) : master.commission != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = passportId != null ? passportId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (adoptionDate != null ? adoptionDate.hashCode() : 0);
        result = 31 * result + category;
        result = 31 * result + (commission != null ? commission.hashCode() : 0);
        temp = Double.doubleToLongBits(salary);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "type_id", nullable = false)
    public OrderType getOrderTypeByCategory() {
        return orderTypeByCategory;
    }

    public void setOrderTypeByCategory(OrderType orderTypeByCategory) {
        this.orderTypeByCategory = orderTypeByCategory;
    }

    @OneToMany(mappedBy = "masterByMasterMasterId")
    public Collection<Order1> getOrdersByPassportId() {
        return ordersByPassportId;
    }

    public void setOrdersByPassportId(Collection<Order1> ordersByPassportId) {
        this.ordersByPassportId = ordersByPassportId;
    }
}
