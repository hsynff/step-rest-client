package com.forum.admin.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "action")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_action")
    private int id;

    @Column(name = "action_type")
    private String actionType;

    @ManyToMany(mappedBy = "actions")
    private List<Role> roles;

}
