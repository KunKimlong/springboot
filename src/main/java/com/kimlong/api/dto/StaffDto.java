package com.kimlong.api.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StaffDto {
    public StaffDto() {
        //TODO Auto-generated constructor stub
    }
    private int id;
    private String name;
    private String gender;
    private String position;
    private MultipartFile profile;
}
