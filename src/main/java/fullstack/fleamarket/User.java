package fullstack.fleamarket;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "User")
public class User {

    /// TODO: Добавить админов и простых пользователей

    @Id
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "name")
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /// TODO: хранить пароли в хешах
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "dorm")
    private Integer dorm;

    @Column(name = "phone")
    private String phone;

    @Column(name = "photo")
    private String photo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Product> productList;

    protected User() {}

    public User(
            String login,
            String email,
            String password
    ) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDorm() {
        return dorm;
    }

    public void setDorm(Integer dorm) {
        this.dorm = dorm;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}