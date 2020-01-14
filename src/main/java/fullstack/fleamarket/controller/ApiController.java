package fullstack.fleamarket.controller;


import fullstack.fleamarket.model.Product;
import fullstack.fleamarket.repository.ProductDAO;
import fullstack.fleamarket.model.User;
import fullstack.fleamarket.repository.UserDAO;
import fullstack.fleamarket.security.jwt.JwtProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.platform.commons.util.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ApiController {

    private final UserDAO userDAO;
    private final ProductDAO productDAO;

    public ApiController(UserDAO userDAO, ProductDAO productDAO) {
        this.userDAO = userDAO;
        this.productDAO = productDAO;
    }

    @Autowired
    private JwtProvider tokenProvider;

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

    @GetMapping(value = "/products/top_by_categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTopProducts(
            @RequestParam(required = false, name = "categories") List<String> categories,
            @RequestParam(required = false, defaultValue = "10", name = "number") Integer number
    ) {
        JSONObject answer = new JSONObject();
        for (String category : categories) {
            List<Product> products= productDAO.getTopNFromCategory(number, category);
            answer.put(category, new JSONArray(products));
        }
        return answer.toString();
    }

    @GetMapping(value = "/products/top", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTopProducts(
            @RequestParam(required = false, defaultValue = "10", name = "number") Integer number
    ) {
        JSONObject answer = new JSONObject();
        List<Product> products = productDAO.getTopN(number);
        answer.put("products", new JSONArray(products));
        return answer.toString();
    }

    @GetMapping(value = "/products/by_username", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getProductsForUser(@RequestParam(name = "username") String username) {
        JSONObject answer = new JSONObject();
        List<User> user = userDAO.findByUsernameEquals(username);
        if (!user.isEmpty()) {
            List<Product> products = productDAO.findByUserIdEquals(user.get(0).getId());
            JSONArray array = new JSONArray(products);
            answer = new JSONObject();
            answer.put("products", array);
        } else {
            answer.put("status", 404);
            answer.put("message", "Пользователь с таким логином не найден");
        }
        return answer.toString();
    }

    @GetMapping(value = "/products/my", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getProductsForMe(@RequestHeader(value = "Authorization") String auth) {
        JSONObject answer = new JSONObject();
        String username = tokenProvider.getUserNameFromJwtToken(auth.replace("Bearer ", ""));
        return getProductsForUser(username);
    }

    /*@PostMapping(value = "/change_password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
    }*/

    /*@PostMapping(value = "/create_user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
    }*/

    @PostMapping(value = "/products/manage/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createProduct(@RequestBody String message,
                                @RequestHeader(value = "Authorization") String auth) {
        JSONObject msg = new JSONObject(message);
        JSONObject answer = new JSONObject();

        String username = tokenProvider.getUserNameFromJwtToken(auth.replace("Bearer ", ""));
        User user = userDAO.findByUsernameEquals(username).get(0);
        Product product = new Product(user.getId(), msg.getString("name"),
                msg.getInt("price"), msg.getString("category"));

        if (msg.has("description")) {
            product.setDescription(msg.getString("description"));
        }

        if (msg.has("dorm")) {
            product.setDorm(msg.getInt("dorm"));
        }

        productDAO.save(product);

        answer.put("status", 0);
        answer.put("product", product.toJson());
        return answer.toString();
    }

    @PostMapping(value = "/products/manage/delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteProduct(@RequestParam(name = "product_id") Long product_id,
                                @RequestHeader(value = "Authorization") String auth) {
        String username = tokenProvider.getUserNameFromJwtToken(auth.replace("Bearer ", ""));
        User user = userDAO.findByUsernameEquals(username).get(0);
        Product product = productDAO.findById(product_id).get();

        JSONObject answer = new JSONObject();
        if (!product.getUserId().equals(user.getId())) {
            answer.put("status", 1);
            return answer.toString();
        }

        productDAO.deleteById(product_id);

        answer.put("status", 0);
        return answer.toString();
    }
}