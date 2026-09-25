package com.gisforum;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private PostRepository postRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private CommentRepository commentRepository; // 👉 Khai báo thêm CommentRepository

    @Override
    public void run(String... args) throws Exception {
        // Xóa sạch dữ liệu cũ để nạp mới (Nhớ xóa comment trước vì nó phụ thuộc Post)
        commentRepository.deleteAll();
        postRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();

        // 1. TẠO USERS
        User admin = createUser("Tan Nguyen", "admin", "123", "ADMIN");
        User gv = createUser("Giang vien", "gv@gmail.com", "123", "ADMIN");
        User sv1 = createUser("Sinh vien1", "sv1@gmail.com", "123", "USER");
        User sv2 = createUser("Sinh vien2", "sv2@gmail.com", "123", "USER");
        User sv3 = createUser("Sinh vien3", "sv3@gmail.com", "123", "USER");

        // 2. TẠO CATEGORIES 
        Category c1 = createCategory("Viễn thám & Vệ tinh", "Phân tích dữ liệu Sentinel-2, Landsat 8.");
        Category c2 = createCategory("Mô hình hóa & Phân tích", "Ứng dụng mô hình AHP, chồng xếp bản đồ.");
        Category c3 = createCategory("Hệ quản trị CSDL & Bản đồ", "Quản lý dữ liệu địa chính Ninh Thuận.");

        // 3. TẠO POSTS (Phải gán biến p1, p2, p3 để lát nạp bình luận)
        Post p1 = createPost("Phân vùng nguy cơ cháy rừng tỉnh Ninh Thuận năm 2025", 
            "[BÁO CÁO KỸ THUẬT CHI TIẾT] - XÂY DỰNG MÔ HÌNH CẢNH BÁO CHÁY RỪNG GIAI ĐOẠN 2025-2030...", 
            11.831, 108.790, "/img/ban-do-phan-vung-2025.jpg", c1);

        Post p2 = createPost("Hồi cứu biến động nguy cơ cháy rừng giai đoạn 2019", 
            "Nghiên cứu so sánh giữa năm 2019 và hiện tại nhằm đánh giá tác động của biến đổi khí hậu...", 
            11.594, 108.816, "/img/ban-do-phan-vung-2019.jpg", c2);

        Post p3 = createPost("Giám sát điểm nhiệt dị thường năm 2025 qua cảm biến VIIRS", 
            "Báo cáo tổng hợp các điểm cháy (Active Fires) phát hiện qua vệ tinh Suomi NPP...", 
            11.725, 109.020, "/img/ban-do-diem-chay-2025.jpg", c1);

        // Bài 4
        createPost("Ảnh hưởng của ENSO đến nhiệt độ bề mặt đất Ninh Thuận", 
            "Phân tích chuỗi thời gian nhiệt độ từ năm 2020 đến 2025. Hiện tượng El Nino làm tăng 1.5 độ C...", 
            11.562, 108.990, "/img/ban-do-nhiet-do-enso.jpg", c1);

        // Bài 5
        createPost("Thành lập bản đồ hạn hán bằng chỉ số TVDI", 
            "Sử dụng không gian LST-NDVI để xác định mức độ hạn hán tại các huyện ven biển...", 
            11.416, 109.022, "/img/ban-do-han-han-tvdi.jpg", c2);

        // Bài 6
        createPost("Đánh giá suy thoái thảm thực vật khu vực Vườn Quốc gia Núi Chúa", 
            "Chỉ số NDVI giảm 15% trong vòng 5 năm qua. Cần có biện pháp phục hồi khẩn cấp...", 
            11.758, 109.186, "/img/ban-do-thuy-thoai-ndvi.jpg", c1);

        // Bài 7
        createPost("Quản trị cơ sở dữ liệu ranh giới hành chính bằng PostGIS", 
            "Mô hình hóa hệ thống và hiệu chỉnh ranh giới không gian các huyện sau sát nhập hành chính năm 2025...", 
            11.332, 108.878, "/img/ban-do-sau-sat-nhap.jpg", c3);

        // Bài 8
        createPost("Đánh giá độ chính xác phân loại lớp phủ năm 2025", 
            "Kiểm chứng độ tin cậy bản đồ ảnh vệ tinh với 300 điểm mẫu. Hệ số Kappa đạt 0.84...", 
            11.710, 109.150, "/img/ban-do-kiem-chung-2025.jpg", c2);

        // Bài 9
        createPost("Hậu kiểm dữ liệu viễn thám giai đoạn 2019", 
            "Quy trình kiểm chứng hồi cứu dữ liệu năm 2019 để đánh giá sự tiến bộ của các thuật toán học máy...", 
            11.666, 109.116, "/img/ban-do-kiem-chung-2019.jpg", c2);

        // 👉 4. TẠO COMMENTS (Nạp bình luận mẫu)
        createComment("Đề tài rất thực tế! Thầy có thể chia sẻ thêm về nguồn dữ liệu nhiệt độ không ạ?", "Sinh vien1", p1);
        createComment("Mô hình này mình test thử độ chính xác trên tập kiểm tra là bao nhiêu phần trăm vậy nhóm?", "Giang vien", p1);
        
        createComment("Theo em thấy năm 2019 số lượng điểm cháy ít hơn nhiều so với năm nay. Có phải do năm đó mưa nhiều hơn?", "Sinh vien2", p2);
        
        createComment("Cảm biến VIIRS có độ phân giải không gian không cao bằng Landsat 8, tại sao nhóm không dùng Landsat?", "Sinh vien3", p3);
        createComment("VIIRS chụp liên tục 2 lần/ngày nên phát hiện cháy rừng theo thời gian thực tốt hơn Landsat (16 ngày) em nhé.", "Tan Nguyen", p3);


        System.out.println(">> ĐÃ NẠP XONG DATA: USERS, CATEGORIES, 9 POSTS VÀ COMMENTS!");
    }

    // Hàm tạo User (Sửa lại kiểu trả về là User để gán vào biến)
    private User createUser(String name, String email, String pass, String role) {
        User u = new User(); u.setName(name); u.setEmail(email); u.setPassword(pass); u.setRole(role);
        return userRepository.save(u);
    }

    // Hàm tạo Category (Sửa lại kiểu trả về là Category)
    private Category createCategory(String name, String desc) {
        Category c = new Category(); c.setName(name); c.setDescription(desc);
        return categoryRepository.save(c);
    }

    // Hàm tạo Post (Sửa lại kiểu trả về là Post)
    private Post createPost(String title, String content, double lat, double lng, String img, Category category) {
        Post p = new Post();
        p.setTitle(title); p.setContent(content); p.setLat(lat); p.setLng(lng); p.setImageUrl(img);
        p.setCategory(category);
        return postRepository.save(p);
    }

    // 👉 Hàm tạo Comment mới
    private void createComment(String content, String authorName, Post post) {
        Comment c = new Comment();
        c.setContent(content);
        c.setAuthor(authorName);
        c.setPost(post);
        commentRepository.save(c);
    }
}