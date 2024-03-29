package fullstack.fleamarket.controller;

import fullstack.fleamarket.model.Product;
import fullstack.fleamarket.repository.ProductDAO;
import fullstack.fleamarket.model.User;
import fullstack.fleamarket.repository.UserDAO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    /* Это тестировочный контроллер
    *  Через него можно ручками добавлять данные в базу
    */

    private final UserDAO userDAO;
    private final ProductDAO productDAO;

    public TestController(UserDAO userDAO, ProductDAO productDAO) {
        this.userDAO = userDAO;
        this.productDAO = productDAO;
    }

    @GetMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE)
    public String home(){
        return "Welcome to the flea market!";
    }

    @GetMapping(value = "/init", produces=MediaType.TEXT_PLAIN_VALUE)
    public String init() {

        User user = new User("milltanya", "Tanya", "miller.to@phystech.edu", "1111");
        user.setName("Таня Миллер");
        user.setDorm(12);
        user.setPhone("89851151743");
        userDAO.save(user);

        Product product = new Product(userDAO.findByUsernameEquals("milltanya").get(0).getId(), "Комод", 200000, "furniture");
        product.setDescription("Белый комод икеа 90*80*40");
        product.setDorm(12);
        productDAO.save(product);

        return "В базу записалось, что есть пользователь Таня и что она хочет продать белый комод";
    }
}