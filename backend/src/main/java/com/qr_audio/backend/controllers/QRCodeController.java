package com.qr_audio.backend.controllers;

import com.qr_audio.backend.dto.ApiResponse;
import com.qr_audio.backend.services.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qr")
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;

    // API dành cho Admin tạo mã QR cho một hiện vật
    @PostMapping("/generate/{exhibitId}")
    public ResponseEntity<ApiResponse> generateQR(@PathVariable Long exhibitId) {
        try {
            String base64Image = qrCodeService.createQRCodeForExhibit(exhibitId);
            return ResponseEntity.ok(new ApiResponse(200, "Tạo mã QR thành công", base64Image));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(400, "Lỗi tạo QR: " + e.getMessage(), null));
        }
    }
}