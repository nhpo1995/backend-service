# Backend Service

Dự án Backend Service được xây dựng bằng Spring Boot, cung cấp các tính năng xác thực và quản lý người dùng với bảo mật dựa trên JWT (OAuth2 Resource Server).

## 🚀 Công nghệ sử dụng

- **Java 17**
- **Spring Boot 3.x** (với Spring Security, Spring Data JPA)
- **Database**: PostgreSQL
- **Security**: OAuth2 Resource Server (JWT)
- **Documentation**: Springdoc OpenAPI (Swagger UI)
- **Lombok**: Giúp giảm thiểu boilerplate code

## ⚙️ Cấu hình hệ thống

Dự án sử dụng các biến môi trường để cấu hình. Bạn cần thiết lập các thông số sau trong tệp `.env` hoặc cấu hình hệ thống:

- `DB_USERNAME`: Tên đăng nhập database.
- `DB_PASSWORD`: Mật khẩu database.
- `JWT_SECRET_KEY`: Khóa bí mật dùng để ký và giải mã JWT (độ dài tối thiểu 32 ký tự).

### Ví dụ cấu hình `application-dev.yaml`
Ứng dụng mặc định chạy ở profile `dev` trên cổng `8080`.

## 🛠 Hướng dẫn cài đặt

1. **Clone repository**:
   ```bash
   git clone <repository-url>
   cd backend-service
   ```

2. **Cấu hình Database**:
   - Tạo database có tên `backend-service` trong PostgreSQL.

3. **Build dự án**:
   ```bash
   ./mvnw clean install
   ```

4. **Chạy ứng dụng**:
   ```bash
   ./mvnw spring-boot:run
   ```

## 📖 API Documentation

Sau khi ứng dụng khởi chạy thành công, bạn có thể truy cập tài liệu API tại:

- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **API Docs**: [http://localhost:8080/api-docs](http://localhost:8080/api-docs)

## 🔐 Lưu ý về Bảo mật (JWT)

Dự án sử dụng Spring Security OAuth2 Resource Server. Một lưu ý quan trọng:

- **Lỗi 401 trên Public Endpoints**: Nếu bạn gửi kèm header `Authorization` với Token không hợp lệ đến các endpoint như `/api/v1/auth/login`, hệ thống sẽ trả về lỗi **401 Unauthorized** ngay lập tức thay vì cho phép truy cập (do bộ lọc JWT chạy trước bộ lọc phân quyền). 
- **Khắc phục**: Đảm bảo không gửi header `Authorization` khi gọi các API công khai (Login, Register).

## 🏗 Cấu trúc thư mục chính

- `src/main/java/.../configuration`: Cấu hình Security, JWT, JPA.
- `src/main/java/.../controller`: Các lớp định nghĩa endpoint API.
- `src/main/java/.../entity`: Các lớp thực thể (User, BaseEntity).
- `src/main/java/.../service`: Logic xử lý nghiệp vụ.
- `src/main/java/.../security`: Xử lý chi tiết về UserDetailsService.
