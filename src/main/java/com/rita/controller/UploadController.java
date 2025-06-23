package com.rita.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UploadController {
    
    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, HttpSession session, HttpServletRequest request) throws IOException {
        // Check if the user is logged in
        String username = (String) session.getAttribute("loginUser");
        if (username == null) {
            return ResponseEntity.status(401).body("Unauthorized: Please log in first.");
        }

        // Validate the file type
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return ResponseEntity.badRequest().body("Invalid file type. Please upload an image.");
        }

        // Validate the file size
        File folder = new File(UPLOAD_DIR);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        
        // Generate a unique filename
        String original = file.getOriginalFilename();
        String suffix = "";
        if (original != null && original.contains(".")) {
            suffix = original.substring(original.lastIndexOf("."));
        }
        String filename = UUID.randomUUID().toString().replace("-", "") + suffix;
        File dest = new File(folder, filename);
        file.transferTo(dest);

        // Construct the URL for the uploaded file
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/uploads/" + filename;

        Map<String, Object> res = new HashMap<>();
        res.put("success", true);
        res.put("url", url);
        return ResponseEntity.ok(res);
    }                       
}
