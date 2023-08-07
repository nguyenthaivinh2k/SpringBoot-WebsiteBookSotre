package com.example.library.repository;

import com.example.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//@EnableJpaRepositories
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    // Customer
    @Query("select p from Product p order by p.is_activated DESC, p.id DESC ")
    Page<Product> pageProduct(Pageable pageable); // = findALl(Pageable pageable)
    @Query("select p from Product p where p.name like %?1%  or p.category.name like %?1%")
    Page<Product> searchProduct(String keyWord, Pageable pageable);

    // Customer
//    @Query("select p from Product p where p.name like %?1%  or p.category.name like %?1%  or p.costPrice <= ?1")
//    List<Product> searchProduct(String Keyword);
    @Query("select p from Product p where p.is_activated=true and p.is_deleted=false order by p.id desc")
    List<Product> getAllProducts();
    @Query(value = "select * from products p where p.is_activated=true and p.is_deleted=false order by rand() asc limit 4", nativeQuery=true)
    List<Product> listViewProducts();
    @Query(value = "select * from products p where p.category_id like ?1 and p.is_activated=true and p.is_deleted=false order by rand() asc limit 6", nativeQuery=true)
    List<Product> getReletedProducts(Long categoryId);
    @Query("select p from Product p inner join Category c on c.id = p.category.id where c.id = ?1 and p.is_activated=true and p.is_deleted=false")
    List<Product> getProductsInCategory(Long categoryId);
    @Query("select p from Product p where p.is_activated=true and p.is_deleted=false order by p.costPrice desc")
    List<Product> filterHightPrice();
    @Query("select p from Product p where p.is_activated=true and p.is_deleted=false order by p.costPrice asc") // asc là mặc định
    List<Product> filterLowPrice();
}
