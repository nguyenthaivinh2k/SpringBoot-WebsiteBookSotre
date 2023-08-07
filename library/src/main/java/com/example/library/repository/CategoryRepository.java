package com.example.library.repository;

import com.example.library.dto.CategoryDTO;
import com.example.library.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
//@EnableJpaRepositories
@Repository
public interface CategoryRepository  extends JpaRepository<Category,Long> {
    @Query("select c from Category c where c.is_activated = true and c.is_deleted=false")
    List<Category> findAllByActive();
    @Query("SELECT p FROM Category p ORDER BY p.is_deleted ASC")
    List<Category> findAllOrderByStatus();
// customer
    @Query("select new com.example.library.dto.CategoryDTO(c.id,c.name,count(p.category.id)) from Category c inner join Product p on p.category.id = c.id" +
            " where c.is_activated = true and c.is_deleted=false group by c.id")
    List<CategoryDTO> getCategoryAndProduct();
}
