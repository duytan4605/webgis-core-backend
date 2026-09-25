package com.gisforum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/export")
// Dùng dòng này để mở toang cửa CORS, cho phép Vue.js lấy file thoải mái
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.OPTIONS})
public class ExportController {

    @Autowired
    private ExportService exportService;

    // --- 1. API Xuất báo cáo Excel ---
    @GetMapping("/excel")
    public ResponseEntity<?> exportExcel() {
        try {
            byte[] fileContent = exportService.exportToExcel();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", "ThongKe_DoanhThu_WebGIS.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileContent);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Lỗi Backend khi xuất Excel: " + e.getMessage());
        }
    }

    // --- 2. API Xuất báo cáo PDF ---
    @GetMapping("/pdf")
    public ResponseEntity<?> exportPdf() {
        try {
            byte[] fileContent = exportService.exportToPdf();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "ThongKe_DoanhThu_WebGIS.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileContent);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Lỗi Backend khi xuất PDF: " + e.getMessage());
        }
    }
}