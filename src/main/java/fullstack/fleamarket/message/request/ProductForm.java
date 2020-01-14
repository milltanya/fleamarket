package fullstack.fleamarket.message.request;

import javax.validation.constraints.NotBlank;

public class ProductForm {
    @NotBlank
    private String username;

    @NotBlank
    private String name;

    @NotBlank
    private Long price;

    @NotBlank
    private String category;

    @NotBlank
    private String description;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
