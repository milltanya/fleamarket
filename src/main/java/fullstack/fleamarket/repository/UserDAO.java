package fullstack.fleamarket.repository;


import fullstack.fleamarket.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO extends CrudRepository<User, String> {
    List<User> findByUsernameEquals(String username);

    Boolean existsByUsernameEquals(String username);

    Boolean existsByEmailEquals(String email);
}
