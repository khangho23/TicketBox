package com.example.demo.controller.empl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.EmplRespModel;

@Controller
@RequestMapping("/empl/login")
public class LoginController {
    private String PATH = "empl";

    @GetMapping(value = { "/", "" })
    public String getLogin(Model Model) {
        Model.addAttribute("user", new EmplRespModel());
        return PATH + "/login";
    }

    @PostMapping(value = { "/", "" })
    public String postLogin(@ModelAttribute("user") EmplRespModel model) {
        System.out.println(model.toString());
        return "redirect:/empl";
    }
}
