package fullstack.fleamarket.model;

import org.json.JSONObject;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Product")
public class Product {

    /// TODO: Добавить картинки

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "userId", nullable = false)
    private Long userId;

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
            Long userId,
            String name,
            Integer price,
            String category
    ) {
        this.userId = userId;
        this.name = name;
        this.timestamp = LocalDateTime.now().toString();
        this.price = price;
        this.category = category;
    }

    public Long getId() { return id; }

    public Long getUserId() {
        return userId;
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

    public JSONObject toJson() {
        JSONObject answer = new JSONObject();
        answer.put("id", this.id);
        answer.put("userId", this.userId);
        answer.put("category", this.category);
        answer.put("name", this.name);
        answer.put("description", this.description);
        answer.put("price", this.price);

        return answer;
    }
}