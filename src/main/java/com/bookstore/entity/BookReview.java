package com.bookstore.entity;
import jakarta.persistence.*;
@Entity public class BookReview {
@Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
@Column(nullable=false) private String username;
@ManyToOne(optional=false) private Book book;
private int rating;
@Column(length=1000) private String comment;
public Long getId(){return id;}
public String getUsername(){return username;} public void setUsername(String v){username=v;}
public Book getBook(){return book;} public void setBook(Book v){book=v;}
public int getRating(){return rating;} public void setRating(int v){rating=v;}
public String getComment(){return comment;} public void setComment(String v){comment=v;}
}
