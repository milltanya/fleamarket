package fullstack.fleamarket;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

    /// TODO: Добавить админов и простых пользователей

    @Id
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "name")
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /// TODO: хранить пароли в хеша
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "dorm")
    private Integer dorm;

    @Column(name = "phone")
    private String phone;

    @Column(name = "photo")
    private String photo;

    protected User() {}

    /// TODO: Сделать все аргументы именованными, чтобы в контроллере извлекать их из json
    public User(
            String login,
            String name,
            String email,
            String password,
            Integer dorm,
            String phone
    ) {
        this.login = login;
        this.name = name;
        this.email = email;
        this.password = password;
        this.dorm = dorm;
        this.phone = phone;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
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

    public String getPhone() {
        return phone;
    }
}