package com.forum.main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private int id;
    private String desc;
    private LocalDateTime writeDate;
    private Topic topic;
    private User user;
}
