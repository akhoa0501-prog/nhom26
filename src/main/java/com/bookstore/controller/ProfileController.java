package com.bookstore.controller;
import com.bookstore.repository.AppUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.nio.file.*;
import java.util.UUID;
@Controller
public class ProfileController {
 private final AppUserRepository users; private final PasswordEncoder encoder;
 public ProfileController(AppUserRepository u,PasswordEncoder e){users=u;encoder=e;}
 @GetMapping("/account") public String account(Authentication a,Model m){m.addAttribute("profile",users.findByUsername(a.getName()).orElseThrow());return "account";}
 @PostMapping("/account") public String save(Authentication a,@RequestParam String fullName,@RequestParam String email,@RequestParam String phone,@RequestParam String address,RedirectAttributes r){
  var u=users.findByUsername(a.getName()).orElseThrow();if(fullName.isBlank()){r.addFlashAttribute("error","Họ tên không được để trống.");return "redirect:/account";}
  if(!email.isBlank()&&users.existsByEmail(email)&&!email.equalsIgnoreCase(u.getEmail()==null?"":u.getEmail())){r.addFlashAttribute("error","Email đã được sử dụng.");return "redirect:/account";}
  u.setFullName(fullName.trim());u.setEmail(email.isBlank()?null:email.trim());u.setPhone(phone.trim());u.setAddress(address.trim());users.save(u);r.addFlashAttribute("success","Đã cập nhật hồ sơ.");return "redirect:/account";
 }
 @PostMapping("/account/password") public String password(Authentication a,@RequestParam String currentPassword,@RequestParam String newPassword,@RequestParam String confirmPassword,RedirectAttributes r){
  var u=users.findByUsername(a.getName()).orElseThrow();if(!encoder.matches(currentPassword,u.getPassword()))r.addFlashAttribute("error","Mật khẩu hiện tại không đúng.");
  else if(newPassword.length()<8)r.addFlashAttribute("error","Mật khẩu mới cần ít nhất 8 ký tự.");
  else if(!newPassword.equals(confirmPassword))r.addFlashAttribute("error","Xác nhận mật khẩu không khớp.");
  else{u.setPassword(encoder.encode(newPassword));users.save(u);r.addFlashAttribute("success","Đã đổi mật khẩu.");}return "redirect:/account";
 }
 @PostMapping("/account/avatar") public String avatar(Authentication a,@RequestParam("avatar") MultipartFile file,RedirectAttributes r){
  try{if(file.isEmpty()||file.getSize()>2_000_000)throw new IllegalArgumentException("Ảnh phải nhỏ hơn 2 MB.");
   String type=file.getContentType();String ext= switch(type==null?"":type){case "image/png"->".png";case "image/jpeg"->".jpg";case "image/webp"->".webp";default->throw new IllegalArgumentException("Chỉ nhận PNG, JPG, WEBP.");};
   byte[] bytes=file.getBytes();boolean valid=ext.equals(".png")&&bytes.length>=8&&(bytes[0]&255)==137&&(bytes[1]&255)==80 || ext.equals(".jpg")&&bytes.length>=3&&(bytes[0]&255)==255&&(bytes[1]&255)==216 || ext.equals(".webp")&&bytes.length>=12&&new String(bytes,0,4,java.nio.charset.StandardCharsets.US_ASCII).equals("RIFF")&&new String(bytes,8,4,java.nio.charset.StandardCharsets.US_ASCII).equals("WEBP");
   if(!valid)throw new IllegalArgumentException("Tệp không đúng định dạng ảnh.");
   Path dir=Paths.get("uploads","avatars");Files.createDirectories(dir);String name=UUID.randomUUID()+ext;Files.write(dir.resolve(name),bytes,StandardOpenOption.CREATE_NEW);
   var u=users.findByUsername(a.getName()).orElseThrow();u.setAvatarUrl("/uploads/avatars/"+name);users.save(u);r.addFlashAttribute("success","Đã đổi ảnh đại diện.");
  }catch(Exception ex){r.addFlashAttribute("error",ex.getMessage());}return "redirect:/account";
 }
}
