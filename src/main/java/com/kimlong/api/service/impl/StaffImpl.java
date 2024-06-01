package com.kimlong.api.service.impl;

import java.util.List;

import com.kimlong.api.entity.Staff;
import com.kimlong.api.repository.StaffRepository;
import com.kimlong.api.service.StaffService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class StaffImpl implements StaffService {
    @Autowired
    private StaffRepository staffrepo;
    @Override
    public List<Staff> getAllStaff(){
        return staffrepo.findAll();
    }
    @Override
    public Staff getStaffByID(int id){
        return staffrepo.findById(id).get();
     }
    //  @Override
    // Staff getStaffByName(String name){
    //     return staffrepo.findBy(null, null);
    // }
    @Override
    public void updateStaff(Staff staff){
        staffrepo.save(staff);
    }
    @Override
    public void deleteStaff(int id){
        staffrepo.deleteById(id);
    }
    @Override
    public void addStaff(Staff staff){
        staffrepo.save(staff);
    }
}
