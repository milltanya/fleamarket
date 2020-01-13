package fullstack.fleamarket.repository;


import fullstack.fleamarket.model.Product;
import fullstack.fleamarket.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDAO extends CrudRepository<Product, Long> {

    @Query(nativeQuery = true, value = "SELECT DISTINCT category FROM Product")
    List<String> findDistinctCategory();

    @Query(nativeQuery = true, value = "SELECT * FROM Product WHERE category = :category ORDER BY timestamp DESC limit :number")
    List<Product> getTopNFromCategory(
            @Param("number") Integer number,
            @Param("category") String category);

    @Query(nativeQuery = true, value = "SELECT * FROM Product ORDER BY timestamp DESC limit :number")
    List<Product> getTopN(@Param("number") Integer number);

    List<Product> findByUserIdEquals(Long userId);
}