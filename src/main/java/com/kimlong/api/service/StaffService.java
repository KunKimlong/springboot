package com.kimlong.api.service;

// import org.hibernate.mapping.List;
import com.kimlong.api.entity.Staff;
import java.util.List;


public interface StaffService {
    List<Staff> getAllStaff();
    Staff getStaffByID(int id);
    // Staff getStaffByName(String name);
    void updateStaff(Staff staff);
    void deleteStaff(int id);
    void addStaff(Staff staff);
}