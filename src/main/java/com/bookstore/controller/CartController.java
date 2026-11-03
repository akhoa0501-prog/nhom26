package com.bookstore.controller;
import com.bookstore.entity.*;
import com.bookstore.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;
@Controller
public class CartController {
 private final BookRepository books; private final CustomerOrderRepository orders; private final CartItemRepository carts; private final AppUserRepository users;
 public CartController(BookRepository b,CustomerOrderRepository o,CartItemRepository c,AppUserRepository u){books=b;orders=o;carts=c;users=u;}
 @PostMapping("/cart/add/{id}") @Transactional
 public String add(@PathVariable Long id,Authentication auth){
  Book b=books.findById(id).orElseThrow(); if(b.getStock()<1)return "redirect:/books/"+id;
  CartItem item=carts.findByUsernameAndBookId(auth.getName(),id).orElseGet(()->{CartItem c=new CartItem();c.setUsername(auth.getName());c.setBook(b);return c;});
  item.setQuantity(Math.min(b.getStock(),item.getQuantity()+1));carts.save(item);return "redirect:/cart";
 }
 @GetMapping("/cart") public String viewCart(Authentication auth,Model model){
  var rows=carts.findByUsername(auth.getName());BigDecimal total=BigDecimal.ZERO;
  for(var row:rows)total=total.add(row.getBook().getPrice().multiply(BigDecimal.valueOf(row.getQuantity())));
  model.addAttribute("rows",rows);model.addAttribute("total",total);
  model.addAttribute("profile",users.findByUsername(auth.getName()).orElse(null));return "cart";
 }
 @PostMapping("/cart/update") public String update(@RequestParam Long bookId,@RequestParam int quantity,Authentication auth){
  carts.findByUsernameAndBookId(auth.getName(),bookId).ifPresent(c->{if(quantity<=0)carts.delete(c);else{c.setQuantity(Math.min(Math.max(1,quantity),c.getBook().getStock()));if(c.getQuantity()==0)carts.delete(c);else carts.save(c);}});return "redirect:/cart";
 }
 @PostMapping("/cart/remove/{id}") public String remove(@PathVariable Long id,Authentication auth){carts.findByUsernameAndBookId(auth.getName(),id).ifPresent(carts::delete);return "redirect:/cart";}
 @PostMapping("/checkout") @Transactional public String checkout(@RequestParam String receiverName,@RequestParam String phone,@RequestParam String address,
  @RequestParam(defaultValue="COD") String paymentMethod,@RequestParam(required=false) List<Long> selectedIds,Authentication auth,Model model){
  if(selectedIds==null||selectedIds.isEmpty()){model.addAttribute("error","Hãy chọn ít nhất một sách để thanh toán.");return viewCart(auth,model);}
  List<CartItem> chosen=carts.findByUsername(auth.getName()).stream().filter(c->selectedIds.contains(c.getBook().getId())).toList();
  if(chosen.isEmpty())return "redirect:/cart";
  if(receiverName.isBlank()||phone.isBlank()||address.isBlank()){model.addAttribute("error","Vui lòng nhập đủ thông tin nhận hàng.");return viewCart(auth,model);}
  for(CartItem c:chosen)if(c.getQuantity()<1||c.getBook().getStock()<c.getQuantity()){model.addAttribute("error","Có sách không đủ số lượng. Hãy cập nhật giỏ hàng.");return viewCart(auth,model);}
  CustomerOrder order=new CustomerOrder();order.setUsername(auth.getName());order.setReceiverName(receiverName.trim());order.setPhone(phone.trim());order.setAddress(address.trim());
  paymentMethod="QR".equals(paymentMethod)?"QR":"COD";order.setPaymentMethod(paymentMethod);order.setPaymentStatus("CHUA_THANH_TOAN");order.setStatus("QR".equals(paymentMethod)?"Chờ thanh toán":"Chờ xác nhận");
  BigDecimal total=BigDecimal.ZERO;
  for(CartItem c:chosen){Book b=c.getBook();int qty=c.getQuantity();total=total.add(b.getPrice().multiply(BigDecimal.valueOf(qty)));b.setStock(b.getStock()-qty);books.save(b);
   OrderItem item=new OrderItem();item.setBook(b);item.setBookTitle(b.getTitle());item.setQuantity(qty);item.setUnitPrice(b.getPrice());order.addItem(item);}
  order.setTotal(total);orders.save(order);carts.deleteAll(chosen);
  return "QR".equals(paymentMethod)?"redirect:/orders/"+order.getId()+"/payment":"redirect:/orders";
 }
 @GetMapping("/orders/{id}/payment") public String payment(@PathVariable Long id,Authentication auth,Model model){var o=orders.findById(id).orElseThrow();if(!o.getUsername().equals(auth.getName())||!"QR".equals(o.getPaymentMethod()))return "redirect:/orders";model.addAttribute("order",o);return "payment";}
 @PostMapping("/orders/{id}/payment/simulate") public String simulate(@PathVariable Long id,Authentication auth){var o=orders.findById(id).orElseThrow();if(!o.getUsername().equals(auth.getName()))return "redirect:/orders";if("QR".equals(o.getPaymentMethod())&&"CHUA_THANH_TOAN".equals(o.getPaymentStatus())){o.setPaymentStatus("DA_THANH_TOAN_MO_PHONG");o.setStatus("Chờ xác nhận");orders.save(o);}return "redirect:/orders";}
 @GetMapping("/orders") public String myOrders(Authentication auth,Model model){model.addAttribute("orders",orders.findByUsernameOrderByCreatedAtDesc(auth.getName()));return "orders";}
}
