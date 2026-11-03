# BOOKSTORE JAVA 17 - Hướng dẫn chạy

## Công nghệ
- Java 17, Spring Boot, Spring MVC, Thymeleaf, Spring Data JPA, Spring Security
- MySQL của XAMPP/phpMyAdmin

## 1. Chuẩn bị
1. Cài JDK 17 và VS Code (các extension Java/Spring Boot nếu có).
2. Cài XAMPP, mở XAMPP Control Panel và Start `MySQL`. Apache chỉ cần khi muốn mở phpMyAdmin.
3. Mở `http://localhost/phpmyadmin`, chọn tab SQL, chạy nội dung `database/bookstore.sql`.
4. Mở thư mục `BookStoreJava17` trong VS Code.

## 2. Cấu hình database
Mặc định `src/main/resources/application.properties` dùng:
- URL: `jdbc:mysql://localhost:3306/bookstore_db`
- Username: `root`
- Password: để trống (mặc định thường gặp của XAMPP).
Nếu MySQL của bạn có mật khẩu, sửa `spring.datasource.password`.

## 3. Chạy ứng dụng
Mở Terminal tại thư mục có `pom.xml`, chạy:
- Windows: `mvn spring-boot:run`
- Nếu máy không nhận lệnh `mvn`, dùng Maven for Java trong VS Code hoặc cài Maven; lần đầu cần Internet để tải thư viện.
Sau khi chạy thành công, mở `http://localhost:8080`.

## 4. Tài khoản quản trị
Lần chạy đầu tiên, ứng dụng tự tạo tài khoản admin:
- Tên đăng nhập: `admin`
- Mật khẩu: `Admin@123`
Đổi mật khẩu sau khi đăng nhập trong môi trường sử dụng thật. Đây là tài khoản demo cho bài tập.

## 5. Chức năng
- Trang chủ, tìm kiếm sách, lọc danh mục, xem chi tiết.
- Đăng ký, đăng nhập, đăng xuất, phân quyền USER/ADMIN.
- Quản trị viên: thêm/sửa/xóa sách, xem danh sách đơn hàng.
- Giỏ hàng theo phiên đăng nhập, cập nhật số lượng và đặt hàng; đơn hàng lưu DB.
- Có dữ liệu sách mẫu tự tạo khi database chưa có sách.

## Lưu ý
- Đây là đồ án học tập. Thanh toán hiện là hình thức COD (thanh toán khi nhận hàng), chưa tích hợp cổng thanh toán trực tuyến.
- Email OTP và thanh toán online chưa được tích hợp trong bản này; không trình bày là đã có.
