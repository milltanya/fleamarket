package fullstack.fleamarket.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Product")
public class Product {

    /// TODO: Добавить картинки

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @Column(name = "category")
    private String category;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "dorm")
    private Integer dorm;

    @Column(name = "timestamp", nullable = false)
    private String timestamp;

    @Column(name = "price", nullable = false)
    private Integer price;

    protected Product() {}

    public Product(
            User user,
            String name,
            Integer price
    ) {
        this.user = user;
        this.name = name;
        this.timestamp = LocalDateTime.now().toString();
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDorm() {
        return dorm;
    }

    public void setDorm(Integer dorm) {
        this.dorm = dorm;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Integer getPrice() {
        return price;
    }
}