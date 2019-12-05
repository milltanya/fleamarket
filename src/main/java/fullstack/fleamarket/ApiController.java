package fullstack.fleamarket;


import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/users")
    public Iterable<User> getUsers() {
        return userDAO.findAll();
    }

    @GetMapping("/products")
    public Iterable<Product> getProducts() {
        return productDAO.findAll();
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String login(@RequestBody String message) {
        JSONObject msg = new JSONObject(message);

        String login = msg.getString("login");
        String password = msg.getString("password");

        Optional<User> user = userDAO.findById(login);

        JSONObject answer = new JSONObject();

        if (user.isPresent()) {
            if (user.get().getPassword().equals(password)) {
                answer.put("status", Boolean.TRUE);
            } else {
                answer.put("status", Boolean.FALSE);
                answer.put("message", "Неправильный пароль");
            }
        } else {
            answer.put("status", Boolean.FALSE);
            answer.put("message", "Неправильный логин");
        }

        return answer.toString();
    }

    @PostMapping(value = "/change_password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
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
                answer.put("status", Boolean.TRUE);
            } else {
                answer.put("status", Boolean.FALSE);
                answer.put("message", "Неправильный пароль");
            }
        } else {
            answer.put("status", Boolean.FALSE);
            answer.put("message", "Неправильный логин");
        }

        return answer.toString();
    }

    @PostMapping(value = "/create_user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String createUser(@RequestBody String message) {
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

        answer.put("status",Boolean.TRUE);
        return answer.toString();
    }
}