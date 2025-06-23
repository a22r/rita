package com.rita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import jakarta.servlet.http.*;
import com.rita.service.InfoService;
import com.rita.model.Info;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/info")
public class InfoController {
    @Autowired
    private InfoService infoService;

    // save info
    @PostMapping("/save")
    public ResponseEntity<?> saveInfo(@RequestBody Info info, HttpSession session) {
        String username = (String) session.getAttribute("loginUser");
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }     
        Info saved = infoService.save(info);
        return ResponseEntity.ok(saved);
    }

    // get all info
    @GetMapping("/all")
    public ResponseEntity<List<Info>> getAll() {
        return ResponseEntity.ok(infoService.getAll());
    }

    // get info by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Info> info = infoService.getById(id);
        
        return info.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());          
    }

    // delete info by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }
        infoService.deleteById(id);
        return ResponseEntity.ok("Info deleted successfully");
    }
}