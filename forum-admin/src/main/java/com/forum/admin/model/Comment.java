package com.forum.admin.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comment")
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "write_date")
    private LocalDateTime writeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_topic")
    private Topic topic;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private User user;

}
