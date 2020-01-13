package fullstack.fleamarket.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "User")
public class User {

    /// TODO: Добавить админов и простых пользователей

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

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

    private String roles = "";

    protected User() {}

    public User(
            String username,
            String name,
            String email,
            String password
    ) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getRoles() { return roles; }

    public String getUsername() {
        return username;
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

    public Long getId() { return id; }

    public void setRoles(String roles) { this.roles = roles; }
}