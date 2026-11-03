package com.bookstore.entity;
import jakarta.persistence.*;
@Entity public class FavoriteBook {
@Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
@Column(nullable=false) private String username;
@ManyToOne(optional=false) private Book book;
public Long getId(){return id;}
public String getUsername(){return username;} public void setUsername(String v){username=v;}
public Book getBook(){return book;} public void setBook(Book v){book=v;}
}
