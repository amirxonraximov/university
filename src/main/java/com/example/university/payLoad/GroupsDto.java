package com.example.university.payLoad;

import lombok.Data;

import java.util.List;

@Data
public class GroupsDto {
    private String name;
    private Integer facultyID;
    private List<Integer> studentIDs;
}
