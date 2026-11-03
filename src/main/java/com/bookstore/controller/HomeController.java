package com.bookstore.controller;

import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CategoryRepository;
import org.springframework.stereotype.Controller;
import java.math.BigDecimal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    private final BookRepository books;
    private final CategoryRepository categories;
    @org.springframework.beans.factory.annotation.Autowired private com.bookstore.repository.BookReviewRepository reviews;
    public HomeController(BookRepository books, CategoryRepository categories) {
        this.books = books; this.categories = categories;
    }
    @GetMapping("/")
    public String home(@RequestParam(required = false) String q,
                       @RequestParam(required = false) Long categoryId, @RequestParam(required = false) BigDecimal minPrice, @RequestParam(required = false) BigDecimal maxPrice, Model model) {
        var list = (q != null && !q.isBlank())
            ? books.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(q, q)
            : books.findAll();
        if (categoryId != null) list = list.stream()
            .filter(b -> b.getCategory() != null && categoryId.equals(b.getCategory().getId())).toList();
        if(minPrice!=null)list=list.stream().filter(b->b.getPrice().compareTo(minPrice)>=0).toList();
        if(maxPrice!=null)list=list.stream().filter(b->b.getPrice().compareTo(maxPrice)<=0).toList();
        model.addAttribute("books", list);
        model.addAttribute("categories", categories.findAll());
        model.addAttribute("q", q == null ? "" : q);
        model.addAttribute("categoryId", categoryId);
        return "home";
    }
    @GetMapping("/books/{id}")
    public String detail(@PathVariable Long id, Model model) {
        var book = books.findById(id).orElseThrow();
        model.addAttribute("book", book);model.addAttribute("reviews",reviews.findByBookId(id));
        return "detail";
    }
    @GetMapping("/login") public String login() { return "login"; }
}
