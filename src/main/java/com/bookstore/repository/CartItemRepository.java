package com.bookstore.repository;
import com.bookstore.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface CartItemRepository extends JpaRepository<CartItem,Long>{
 List<CartItem> findByUsername(String username);
 Optional<CartItem> findByUsernameAndBookId(String username,Long bookId);
}
