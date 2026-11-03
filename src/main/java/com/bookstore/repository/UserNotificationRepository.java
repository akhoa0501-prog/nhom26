package com.bookstore.repository;
import com.bookstore.entity.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserNotificationRepository extends JpaRepository<UserNotification,Long>{java.util.List<UserNotification> findByUsernameOrderByCreatedAtDesc(String username);}