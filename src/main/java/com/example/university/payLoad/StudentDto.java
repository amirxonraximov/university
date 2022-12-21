package com.example.university.payLoad;

import lombok.Data;

import java.util.List;

@Data
public class StudentDto {
    private String firstName;
    private String lastName;
    private Integer addressID;
    private List<Integer> subjectIDs;
}
