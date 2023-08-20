package com.example.demo.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/branch")
public class BranchManagementController {
    @GetMapping("")
    public String index() {
        return "components/branch_management.html";
    }
}
