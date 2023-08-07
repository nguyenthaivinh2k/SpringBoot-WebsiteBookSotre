package com.example.library.repository;

import com.example.library.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("select s from ShoppingCart s where s.customer.id = ?1")
    public ShoppingCart findByCustomer(Long customerId);
}
