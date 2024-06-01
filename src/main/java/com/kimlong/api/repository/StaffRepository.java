package com.kimlong.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kimlong.api.entity.Staff;


public interface StaffRepository extends JpaRepository<Staff,Integer>{
    
}
