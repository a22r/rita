package com.rita.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "info")
@Getter
@Setter
public class Info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "last_update_time")
    private LocalDateTime lastUpdateTime;

    public Info() {
        this.lastUpdateTime = LocalDateTime.now();
    }

    public Info(String title, String content) {
        this.title = title;
        this.content = content;
        this.lastUpdateTime = LocalDateTime.now();
    }

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        lastUpdateTime = LocalDateTime.now();
    }
}