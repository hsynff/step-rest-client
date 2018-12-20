package com.forum.admin.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "topic")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_topic")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "share_date")
    private LocalDateTime shareDate;

    @Column(name = "view_count")
    private int viewCount;

    @Transient
    private int commentsCount;

    @Column(name = "status")
    private int status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private User user;

    @OneToMany(mappedBy = "topic")
    private List<Comment> commentList;

    public Topic() {
        commentList = new ArrayList<>();
    }

    public void addComment(Comment comment) {
        commentList.add(comment);
    }

}