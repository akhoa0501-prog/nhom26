package com.bookstore.controller;

import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CategoryRepository;
import com.bookstore.repository.CustomerOrderRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final BookRepository books;
    private final CategoryRepository categories;
    private final CustomerOrderRepository orders;
    @org.springframework.beans.factory.annotation.Autowired private com.bookstore.repository.UserNotificationRepository notifications;
    public AdminController(BookRepository books, CategoryRepository categories, CustomerOrderRepository orders) {
        this.books = books; this.categories = categories; this.orders = orders;
    }
    @GetMapping public String dashboard(Model model) {
        model.addAttribute("bookCount", books.count());
        model.addAttribute("categoryCount", categories.count());
        model.addAttribute("orderCount", orders.count());
        model.addAttribute("revenue",orders.findAll().stream().filter(o->"Hoàn thành".equals(o.getStatus())).map(o->o.getTotal()).reduce(java.math.BigDecimal.ZERO,java.math.BigDecimal::add));
        return "admin";
    }
    @GetMapping("/books") public String books(Model model) {
        model.addAttribute("books", books.findAll()); return "admin-books";
    }
    @GetMapping("/books/new") public String newBook(Model model) {
        model.addAttribute("book", new Book()); model.addAttribute("categories", categories.findAll());
        return "book-form";
    }
    @GetMapping("/books/edit/{id}") public String editBook(@PathVariable Long id, Model model) {
        model.addAttribute("book", books.findById(id).orElseThrow());
        model.addAttribute("categories", categories.findAll()); return "book-form";
    }
    @PostMapping("/books/save") public String saveBook(@Valid @ModelAttribute Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categories.findAll()); return "book-form";
        }
        if (book.getCategory() != null && book.getCategory().getId() != null)
            book.setCategory(categories.findById(book.getCategory().getId()).orElse(null));
        if (book.getCoverUrl() == null || book.getCoverUrl().isBlank())
            book.setCoverUrl("https://placehold.co/240x320?text=Book");
        books.save(book); return "redirect:/admin/books?saved";
    }
    @PostMapping("/books/delete/{id}") public String deleteBook(@PathVariable Long id) {
        books.deleteById(id); return "redirect:/admin/books?deleted";
    }
    @PostMapping("/orders/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        var allowed = java.util.Set.of("Chờ xác nhận", "Đang chuẩn bị", "Đang giao", "Hoàn thành", "Đã hủy");
        if (allowed.contains(status)) {
            var order = orders.findById(id).orElseThrow();
            order.setStatus(status); orders.save(order);var n=new com.bookstore.entity.UserNotification();n.setUsername(order.getUsername());n.setMessage("Đơn hàng #"+id+" chuyển sang trạng thái: "+status);notifications.save(n);
        }
        return "redirect:/admin/orders";
    }
    @GetMapping("/orders") public String orders(Model model) {
        model.addAttribute("orders", orders.findAllByOrderByCreatedAtDesc()); return "admin-orders";
    }
}
