package entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Created by Oleg Dashkevych on 10.05.2015.
 */
@Entity
public class Client {
    private String passportId;

    private Date birthday;

    private String name;

    private Collection<Car> carsByPassportId;

    @Id
    @Column(name = "passport_id", nullable = false, insertable = true, updatable = true, length = 18)
    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    @Basic
    @Column(name = "birthday", nullable = true, insertable = true, updatable = true)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 90)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (passportId != null ? !passportId.equals(client.passportId) : client.passportId != null) return false;
        if (birthday != null ? !birthday.equals(client.birthday) : client.birthday != null) return false;
        if (name != null ? !name.equals(client.name) : client.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = passportId != null ? passportId.hashCode() : 0;
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "clientByClientPassportId")
    public Collection<Car> getCarsByPassportId() {
        return carsByPassportId;
    }

    public void setCarsByPassportId(Collection<Car> carsByPassportId) {
        this.carsByPassportId = carsByPassportId;
    }
}
