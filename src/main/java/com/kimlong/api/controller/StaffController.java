package com.kimlong.api.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kimlong.api.entity.Staff;
import com.kimlong.api.service.StaffService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@AllArgsConstructor
@Setter
@Getter
@RestController
public class StaffController {
    @Autowired
    private StaffService staffservice;

    @GetMapping("/helloworld")
    public String helloWorld() {
        return "HELLO WORLD";
    }

    private String getUrlProfile(String profile) {
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        // System.out.println(uri);
        return uri + "/image/" + profile;

    }

    @GetMapping("/getstaff")
    public ResponseEntity<Object> getstaffs() {
        try {
            List<Staff> staff = staffservice.getAllStaff();

            if (staff.isEmpty()) {
                return new ResponseEntity<>(staff, HttpStatusCode.valueOf(404));
            }
            for (Staff s : staff) {
                s.setProfile(getUrlProfile(s.getProfile()));
            }
            return new ResponseEntity<>(staff, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @PostMapping("/addstaff")
    public ResponseEntity<Object> addStaff(
            @RequestParam("name") String name,
            @RequestParam("gender") String gender,
            @RequestParam("position") String position,
            @RequestParam("profile") MultipartFile profile) {

        Date date = new Date();
        String profileName = date.getTime() + '_' + profile.getOriginalFilename();

        try {
            String path = "public/image/";
            Path uploadPath = Paths.get(path);
            Files.createDirectories(uploadPath);
            try (InputStream inputstream = profile.getInputStream()) {
                Files.copy(inputstream, Paths.get(path + profileName));
            }

        } catch (Exception e) {
            System.out.println("Error Can not move upload file: " + e.getMessage());
        }

        Staff staff = new Staff();
        staff.setName(name);
        staff.setGender(gender);
        staff.setPosition(position);
        staff.setProfile(profileName);

        System.out.println("Staff = " + staff.getName() + " " + staff.getGender() + " " + staff.getPosition() + " "
                + staff.getProfile());
        try {
            staffservice.addStaff(staff);
            return new ResponseEntity<>("Staff Add Success", HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>("Staff Add not Success: " + e.getMessage(), HttpStatusCode.valueOf(400));
        }

    }

    @GetMapping("/getstaff/{id}")
    public ResponseEntity<Object> getStaffByID(@PathVariable int id) {

        try {
            Staff staff = staffservice.getStaffByID(id);
            staff.setProfile(getUrlProfile(staff.getProfile()));
            return new ResponseEntity<>(staff, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>("Id not found", HttpStatusCode.valueOf(404));
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Object> updateStaff(
            @PathVariable int id,
            @RequestParam("name") String name,
            @RequestParam("gender") String gender,
            @RequestParam("position") String position,
            @RequestParam("profile") MultipartFile profile) {

        Staff staff = staffservice.getStaffByID(id);
        String profileName;
        if (profile.getOriginalFilename().isEmpty()) {
           profileName = staff.getProfile();
        } else {
            Date date = new Date();
            profileName = date.getTime() + '_' + profile.getOriginalFilename();
            try {
                String path = "public/image/";
                Path uploadPath = Paths.get(path);
                Files.createDirectories(uploadPath);
                try (InputStream inputstream = profile.getInputStream()) {
                    Files.copy(inputstream, Paths.get(path + profileName));
                }
            } catch (Exception e) {
                System.out.println("Error Can not move upload file: " + e.getMessage());
            }

        }
        staff.setName(name);
        staff.setGender(gender);
        staff.setPosition(position);
        staff.setProfile(profileName);
        try {
            staffservice.updateStaff(staff);
            return new ResponseEntity<>("Update Successfully",HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>("Not found",HttpStatusCode.valueOf(404));
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> deleteStaff(@PathVariable int id){
        try {
            staffservice.deleteStaff(id);
            return new ResponseEntity<>("delete Successfully",HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>("delete not Successfully",HttpStatusCode.valueOf(404));
        }
    }
    
}
