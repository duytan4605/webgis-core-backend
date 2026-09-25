# ⚙️ WebGIS Core API (Backend)

Đây là lõi xử lý nghiệp vụ (RESTful API) cho hệ thống WebGIS - Đồ án tốt nghiệp. Đóng vai trò là trung tâm xử lý dữ liệu, phân quyền bảo mật và kết xuất tài liệu động cho hệ thống.

## 🚀 Tính năng nổi bật (Key Features)
- **Quản lý tài khoản (User Management):** API CRUD tài khoản, tự động phân hạng (Tier: VIP/FREE) và tính toán doanh thu.
- **Export Service (Báo cáo):** Tự động truy xuất Database và "vẽ" ra file thống kê Excel (Apache POI) và PDF (OpenPDF) đẩy stream trực tiếp về Client.
- **System Settings:** API quản lý trạng thái bảo trì toàn hệ thống, bật/tắt luồng đăng ký tài khoản mới.
- **Bảo mật & Cấu hình:** Xử lý Global CORS policy, chặn luồng dữ liệu rác.

## 🛠️ Công nghệ sử dụng (Tech Stack)
- **Core Framework:** Spring Boot 3.2.x, Spring Web
- **Database ORM:** Spring Data JPA, Hibernate
- **Database:** H2 Database (In-memory) / MySQL
- **Thư viện xuất file:** Apache POI (Excel), OpenPDF (PDF)

## ⚙️ Hướng dẫn chạy dự án (Setup)
Dự án sử dụng Maven để quản lý thư viện. Để khởi chạy dự án trên máy cá nhân, vui lòng dùng các lệnh sau:

```bash
# 1. Tải thư viện và build dự án
mvn clean install

# 2. Khởi chạy ứng dụng (Mặc định ở cổng 8085)
mvn spring-boot:run
