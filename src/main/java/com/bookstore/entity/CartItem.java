package com.bookstore.entity;
import jakarta.persistence.*;
@Entity @Table(name="shopping_cart_items",uniqueConstraints=@UniqueConstraint(columnNames={"username","book_id"}))
public class CartItem {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String username;
 @ManyToOne(optional=false) @JoinColumn(name="book_id") private Book book;
 private int quantity;
 public Long getId(){return id;} public String getUsername(){return username;} public void setUsername(String v){username=v;}
 public Book getBook(){return book;} public void setBook(Book v){book=v;}
 public int getQuantity(){return quantity;} public void setQuantity(int v){quantity=v;}
}
