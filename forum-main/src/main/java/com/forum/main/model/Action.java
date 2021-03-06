package com.forum.main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Action {
    private int id;
    private String actionType;
    private List<Role> roleList;
}
