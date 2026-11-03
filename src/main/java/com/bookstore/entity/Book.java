package com.bookstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "books")
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Vui lòng nhập tên sách")
    @Column(nullable = false, length = 180)
    private String title;
    @NotBlank(message = "Vui lòng nhập tác giả")
    @Column(nullable = false, length = 120)
    private String author;
    @NotNull(message = "Vui lòng nhập giá")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;
    @Min(value = 0, message = "Số lượng không được âm")
    private int stock;
    private String coverUrl;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(length = 1000)
    private String description;

    public Book() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getCoverUrl() { return coverUrl; }
    public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
