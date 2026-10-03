package com.qr_audio.backend.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.qr_audio.backend.entities.Exhibit;
import com.qr_audio.backend.entities.QRCode;
import com.qr_audio.backend.exceptions.ResourceNotFoundException;
import com.qr_audio.backend.repositories.ExhibitRepository;
import com.qr_audio.backend.repositories.QRCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.UUID;

@Service
public class QRCodeService {

    @Autowired
    private QRCodeRepository qrCodeRepository;

    @Autowired
    private ExhibitRepository exhibitRepository;

    public String generateUniqueCodeString(Long exhibitId) {
        return "EXHIBIT-" + exhibitId + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public String generateQRCodeImageBase64(String text, int width, int height) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray(); 
        
        return Base64.getEncoder().encodeToString(pngData);
    }

    @Transactional
    public String createQRCodeForExhibit(Long exhibitId) throws Exception {
        // Kiểm tra hiện vật
        Exhibit exhibit = exhibitRepository.findById(exhibitId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy hiện vật với ID: " + exhibitId));

        // Tạo chuỗi định danh QR
        String codeString = generateUniqueCodeString(exhibitId);
        
        // Lưu vào Database
        QRCode qrCode = new QRCode();
        qrCode.setCodeString(codeString);
        qrCode.setExhibit(exhibit);
        qrCodeRepository.save(qrCode);

        // Trả về mã QR
        return generateQRCodeImageBase64(codeString, 300, 300);
    }
}