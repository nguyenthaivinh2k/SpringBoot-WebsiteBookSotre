package com.example.library.repository;

import com.example.library.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OderDetailRepository extends JpaRepository<OrderDetail,Long> {
}
