package com.bookstore.controller;
import com.bookstore.entity.BookReview;
import com.bookstore.entity.FavoriteBook;
import com.bookstore.repository.FavoriteBookRepository;
import com.bookstore.repository.BookReviewRepository;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CustomerOrderRepository;
import com.bookstore.repository.UserNotificationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
@Controller public class ExtrasController {
 private final FavoriteBookRepository favorites; private final BookReviewRepository reviews; private final BookRepository books; private final CustomerOrderRepository orders; private final UserNotificationRepository notifications;
 public ExtrasController(FavoriteBookRepository f,BookReviewRepository r,BookRepository b,CustomerOrderRepository o,UserNotificationRepository n){favorites=f;reviews=r;books=b;orders=o;notifications=n;}
 @GetMapping("/favorites") public String favorites(Authentication a,Model m){m.addAttribute("favorites",favorites.findByUsername(a.getName()));return "favorites";}
 @PostMapping("/favorites/{id}") public String favorite(@PathVariable Long id,Authentication a){var f=favorites.findByUsernameAndBookId(a.getName(),id);if(f.isPresent())favorites.delete(f.get());else{var x=new FavoriteBook();x.setUsername(a.getName());x.setBook(books.findById(id).orElseThrow());favorites.save(x);}return "redirect:/books/"+id;}
 @PostMapping("/books/{id}/reviews") public String review(@PathVariable Long id,@RequestParam int rating,@RequestParam(defaultValue="") String comment,Authentication a){if(rating<1||rating>5||comment.length()>1000)return "redirect:/books/"+id;boolean purchased=orders.findByUsernameOrderByCreatedAtDesc(a.getName()).stream().filter(o->"Hoàn thành".equals(o.getStatus())).anyMatch(o->o.getItems().stream().anyMatch(i->i.getBook()!=null&&id.equals(i.getBook().getId())));if(!purchased)return "redirect:/books/"+id+"?notPurchased";var r=reviews.findByUsernameAndBookId(a.getName(),id).orElseGet(BookReview::new);r.setUsername(a.getName());r.setBook(books.findById(id).orElseThrow());r.setRating(rating);r.setComment(comment);reviews.save(r);return "redirect:/books/"+id;}
 @GetMapping("/notifications") public String notifications(Authentication a,Model m){m.addAttribute("notifications",notifications.findByUsernameOrderByCreatedAtDesc(a.getName()));return "notifications";}
 @GetMapping("/orders/{id}/invoice") public String invoice(@PathVariable Long id,Authentication a,Model m){var o=orders.findById(id).orElseThrow();if(!o.getUsername().equals(a.getName())&&!a.getAuthorities().stream().anyMatch(x->x.getAuthority().equals("ROLE_ADMIN")))throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.FORBIDDEN);m.addAttribute("order",o);return "invoice";}
}