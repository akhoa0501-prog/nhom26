package com.bookstore.repository;
import com.bookstore.entity.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BookReviewRepository extends JpaRepository<BookReview,Long>{java.util.List<BookReview> findByBookId(Long bookId); java.util.Optional<BookReview> findByUsernameAndBookId(String username,Long bookId);}