package com.bookstore.repository;

import com.bookstore.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByUsernameOrderByCreatedAtDesc(String username);
    List<CustomerOrder> findAllByOrderByCreatedAtDesc();
}
