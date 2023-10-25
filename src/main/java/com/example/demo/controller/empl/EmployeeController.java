package com.example.demo.controller.empl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/empl")
public class EmployeeController {
    private String PATH = "/empl";

    @GetMapping(value={"/",""})
    public String index(){
        return PATH.concat("/index");
    }
}
