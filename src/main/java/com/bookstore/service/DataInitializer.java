package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.entity.Category;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {
    private final CategoryRepository categories;
    private final BookRepository books;
    public DataInitializer(CategoryRepository categories, BookRepository books) {
        this.categories = categories; this.books = books;
    }
    @Override public void run(String... args) {
        for (String name : java.util.List.of("Văn học Việt Nam", "Văn học nước ngoài", "Kinh doanh và kỹ năng", "Công nghệ thông tin")) {
            if (categories.findAll().stream().noneMatch(c -> c.getName().equals(name))) categories.save(new Category(name, "Tủ sách " + name));
        }
        var existing = books.findAll();
        seedIfMissing(existing, "Chí Phèo", "Nam Cao", "Văn học Việt Nam", "49000");
        seedIfMissing(existing, "Lão Hạc", "Nam Cao", "Văn học Việt Nam", "63000");
        seedIfMissing(existing, "Số Đỏ", "Vũ Trọng Phụng", "Văn học Việt Nam", "77000");
        seedIfMissing(existing, "Giông Tố", "Vũ Trọng Phụng", "Văn học Việt Nam", "91000");
        seedIfMissing(existing, "Vợ Nhặt", "Kim Lân", "Văn học Việt Nam", "105000");
        seedIfMissing(existing, "Vợ Chồng A Phủ", "Tô Hoài", "Văn học Việt Nam", "119000");
        seedIfMissing(existing, "Những Ngày Thơ Ấu", "Nguyên Hồng", "Văn học Việt Nam", "133000");
        seedIfMissing(existing, "Tuổi Thơ Dữ Dội", "Phùng Quán", "Văn học Việt Nam", "49000");
        seedIfMissing(existing, "Mắt Biếc", "Nguyễn Nhật Ánh", "Văn học Việt Nam", "63000");
        seedIfMissing(existing, "Cho Tôi Xin Một Vé Đi Tuổi Thơ", "Nguyễn Nhật Ánh", "Văn học Việt Nam", "77000");
        seedIfMissing(existing, "Hoàng Tử Bé", "Antoine de Saint-Exupéry", "Văn học nước ngoài", "49000");
        seedIfMissing(existing, "Nhà Giả Kim", "Paulo Coelho", "Văn học nước ngoài", "63000");
        seedIfMissing(existing, "Không Gia Đình", "Hector Malot", "Văn học nước ngoài", "77000");
        seedIfMissing(existing, "Những Người Khốn Khổ", "Victor Hugo", "Văn học nước ngoài", "91000");
        seedIfMissing(existing, "Ông Già Và Biển Cả", "Ernest Hemingway", "Văn học nước ngoài", "105000");
        seedIfMissing(existing, "Kiêu Hãnh Và Định Kiến", "Jane Austen", "Văn học nước ngoài", "119000");
        seedIfMissing(existing, "1984", "George Orwell", "Văn học nước ngoài", "133000");
        seedIfMissing(existing, "Trại Súc Vật", "George Orwell", "Văn học nước ngoài", "49000");
        seedIfMissing(existing, "Giết Con Chim Nhại", "Harper Lee", "Văn học nước ngoài", "63000");
        seedIfMissing(existing, "Cuốn Theo Chiều Gió", "Margaret Mitchell", "Văn học nước ngoài", "77000");
        seedIfMissing(existing, "7 Thói Quen Hiệu Quả", "Stephen R. Covey", "Kinh doanh và kỹ năng", "49000");
        seedIfMissing(existing, "Nghĩ Giàu Làm Giàu", "Napoleon Hill", "Kinh doanh và kỹ năng", "63000");
        seedIfMissing(existing, "Cha Giàu Cha Nghèo", "Robert Kiyosaki", "Kinh doanh và kỹ năng", "77000");
        seedIfMissing(existing, "Tư Duy Nhanh Và Chậm", "Daniel Kahneman", "Kinh doanh và kỹ năng", "91000");
        seedIfMissing(existing, "Sức Mạnh Của Thói Quen", "Charles Duhigg", "Kinh doanh và kỹ năng", "105000");
        seedIfMissing(existing, "Atomic Habits", "James Clear", "Kinh doanh và kỹ năng", "119000");
        seedIfMissing(existing, "Dám Bị Ghét", "Kishimi Ichiro", "Kinh doanh và kỹ năng", "133000");
        seedIfMissing(existing, "Deep Work", "Cal Newport", "Kinh doanh và kỹ năng", "49000");
        seedIfMissing(existing, "Khởi Nghiệp Tinh Gọn", "Eric Ries", "Kinh doanh và kỹ năng", "63000");
        seedIfMissing(existing, "Từ Tốt Đến Vĩ Đại", "Jim Collins", "Kinh doanh và kỹ năng", "77000");
        seedIfMissing(existing, "Clean Code", "Robert C. Martin", "Công nghệ thông tin", "49000");
        seedIfMissing(existing, "The Pragmatic Programmer", "Andrew Hunt", "Công nghệ thông tin", "63000");
        seedIfMissing(existing, "Design Patterns", "Erich Gamma", "Công nghệ thông tin", "77000");
        seedIfMissing(existing, "Refactoring", "Martin Fowler", "Công nghệ thông tin", "91000");
        seedIfMissing(existing, "Introduction to Algorithms", "Thomas H. Cormen", "Công nghệ thông tin", "105000");
        seedIfMissing(existing, "Head First Java", "Kathy Sierra", "Công nghệ thông tin", "119000");
        seedIfMissing(existing, "Effective Java", "Joshua Bloch", "Công nghệ thông tin", "133000");
        seedIfMissing(existing, "Computer Networking: A Top-Down Approach", "James Kurose", "Công nghệ thông tin", "49000");
        seedIfMissing(existing, "Database System Concepts", "Abraham Silberschatz", "Công nghệ thông tin", "63000");
        seedIfMissing(existing, "You Don’t Know JS Yet", "Kyle Simpson", "Công nghệ thông tin", "77000");
    }
    private void seedIfMissing(java.util.List<Book> existing, String title, String author, String categoryName, String price) {
        if (existing.stream().anyMatch(b -> b.getTitle().equalsIgnoreCase(title) && b.getAuthor().equalsIgnoreCase(author))) return;
        Category category = categories.findAll().stream().filter(c -> c.getName().equals(categoryName)).findFirst().orElseThrow();
        add(title, author, price, 20, category, "Sách " + categoryName + " của tác giả " + author + ". Giá tham khảo phục vụ đồ án.");
    }
    private void add(String title, String author, String price, int stock, Category category, String desc) {
        Book b = new Book(); b.setTitle(title); b.setAuthor(author); b.setPrice(new BigDecimal(price));
        b.setStock(stock); b.setCategory(category); b.setDescription(desc);
        b.setCoverUrl("/images/book-placeholder.svg");
        books.save(b);
    }
}
