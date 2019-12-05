package fullstack.fleamarket;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {

    /// TODO: Добавить картинки

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public Long id;

    @Column(name = "user_login", nullable = false)
    private String userLogin;

    @Column(name = "category")
    private String category;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "dorm")
    private Integer dorm;

    @Column(name = "timestamp", nullable = false)
    private String timestamp;

    @Column(name = "price", nullable = false)
    private Integer price;

    protected Product() {}

    public Product(
            String userLogin,
            String category,
            String name,
            String description,
            Integer dorm,
            String timestamp,
            Integer price
    ) {
        this.userLogin = userLogin;
        this.category = category;
        this.name = name;
        this.description = description;
        this.dorm = dorm;
        this.timestamp = timestamp;
        this.price = price;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getDorm() {
        return dorm;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Integer getPrice() {
        return price;
    }
}