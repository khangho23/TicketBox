package com.example.demo.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/branch")
public class BranchManagementController {
    @GetMapping("")
    public String index() {
        
        int a, b;
        a = 10; b = 5;
        if (a >= b) {

        }
        return "components/branch_management.html";
    }
}
