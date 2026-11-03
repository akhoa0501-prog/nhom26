package com.bookstore.repository;
import com.bookstore.entity.FavoriteBook;
import org.springframework.data.jpa.repository.JpaRepository;
public interface FavoriteBookRepository extends JpaRepository<FavoriteBook,Long>{java.util.List<FavoriteBook> findByUsername(String username); java.util.Optional<FavoriteBook> findByUsernameAndBookId(String username,Long bookId);}