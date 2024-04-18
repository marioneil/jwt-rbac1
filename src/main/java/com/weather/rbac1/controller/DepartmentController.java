package com.weather.rbac1.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/department")
public class DepartmentController {

    @GetMapping("/hod")
    @PreAuthorize("hasRole('HOD')")
    public String getHODData(){
        return "HOD DATA";
    }

    @GetMapping("/prof")
    @PreAuthorize("hasRole('PROF')")
    public String getProfData(){
        return "Prof DATA";
    }

}
