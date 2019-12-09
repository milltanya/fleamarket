package fullstack.fleamarket;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final UserDAO userDAO;
    private final ProductDAO productDAO;

    public ApiController(UserDAO userDAO, ProductDAO productDAO) {
        this.userDAO = userDAO;
        this.productDAO = productDAO;
    }

    @GetMapping(value = "/users")
    public Iterable<User> getUsers() {
        return userDAO.findAll();
    }

    @GetMapping(value = "/products")
    public Iterable<Product> getProducts() {
        return productDAO.findAll();
    }

    @GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCategories() {
        List<String> categories = productDAO.findDistinctCategory();
        JSONArray array = new JSONArray(categories);
        JSONObject answer = new JSONObject();
        answer.put("categories", array);
        return answer.toString();
    }

    @GetMapping(value = "/top_products", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTopProducts(
            @RequestParam(required = false, name = "category") String category,
            @RequestParam(required = false, defaultValue = "10", name = "number") Integer number
    ) {
        JSONObject answer = new JSONObject();
        List<Product> products;
        if (category == null) {
            products = productDAO.getTopN(number);
        } else {
            products = productDAO.getTopNFromCategory(number, category);
        }
        JSONArray array = new JSONArray(products);
        answer.put("products", array);
        return answer.toString();
    }

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getProductsForUser(@RequestParam(name = "login") String login) {
        JSONObject answer = new JSONObject();
        Optional<User> user = userDAO.findById(login);
        if (user.isPresent()) {
            List<Product> products = productDAO.findByUserEquals(user.get());
            JSONArray array = new JSONArray(products);
            answer = new JSONObject(user.get());
            answer.put("products", array);
        } else {
            answer.put("status", 404);
            answer.put("message", "Пользователь с таким логином не найден");
        }
        return answer.toString();
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(@RequestBody String message) {
        JSONObject msg = new JSONObject(message);

        String login = msg.getString("login");
        String password = msg.getString("password");

        Optional<User> user = userDAO.findById(login);

        JSONObject answer = new JSONObject();

        if (user.isPresent()) {
            if (user.get().getPassword().equals(password)) {
                answer.put("status", 0);
            } else {
                answer.put("status", 1);
                answer.put("message", "Неправильный пароль");
            }
        } else {
            answer.put("status", 1);
            answer.put("message", "Неправильный логин");
        }

        return answer.toString();
    }

    @PostMapping(value = "/change_password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String changepassword(@RequestBody String message) {
        JSONObject msg = new JSONObject(message);

        String login = msg.getString("login");
        String old_password = msg.getString("old_password");
        String new_password = msg.getString("new_password");

        Optional<User> user = userDAO.findById(login);

        JSONObject answer = new JSONObject();

        if (user.isPresent()) {
            if (user.get().getPassword().equals(old_password)) {
                User u = user.get();
                u.setPassword(new_password);
                userDAO.deleteById(login);
                userDAO.save(u);
                answer.put("status", 0);
            } else {
                answer.put("status", 1);
                answer.put("message", "Неправильный пароль");
            }
        } else {
            answer.put("status", 1);
            answer.put("message", "Неправильный логин");
        }

        return answer.toString();
    }

    @PostMapping(value = "/create_user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createUser(@RequestBody String message) {
        /// TODO: Валидировать данные

        JSONObject msg = new JSONObject(message);
        JSONObject answer = new JSONObject();

        User user = new User(msg.getString("login"), msg.getString("email"), msg.getString("password"));

        if (msg.has("name")) {
            user.setName(msg.getString("name"));
        }

        if (msg.has("dorm")) {
            user.setDorm(msg.getInt("dorm"));
        }

        if (msg.has("phone")) {
            user.setPhone(msg.getString("phone"));
        }

        userDAO.save(user);

        answer.put("status", 0);
        return answer.toString();
    }

    @PostMapping(value = "/create_product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createProduct(@RequestBody String message) {
        JSONObject msg = new JSONObject(message);
        JSONObject answer = new JSONObject();

        User user = userDAO.findById(msg.getString("user")).get();
        Product product = new Product(user, msg.getString("name"), msg.getInt("price"));

        if (msg.has("category")) {
            product.setCategory(msg.getString("category"));
        }

        if (msg.has("description")) {
            product.setDescription(msg.getString("description"));
        }

        if (msg.has("dorm")) {
            product.setDorm(msg.getInt("dorm"));
        }

        productDAO.save(product);

        answer.put("status", 0);
        return answer.toString();
    }
}