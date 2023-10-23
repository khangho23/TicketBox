package com.example.demo.controller.empl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/empl")
public class EmployeeController {
    private String PATH = "empl";

    @GetMapping(value = { "/", "" })
    public String index() {
        return PATH.concat("/index");
    }

    @GetMapping(value = { "/book-seat_{showtimeid}_{emplId}" })
    public String book_seat(@PathVariable("showtimeid") int showtimeid, @PathVariable("emplId") String emplId,
            Model model) {
        model.addAttribute("showtimeid", showtimeid);
        model.addAttribute("emplId", emplId);
        return PATH.concat("/book-seat");
    }

    @GetMapping(value = { "/book-payment_{showtimeid}_{emplId}" })
    public String book_payment(@PathVariable("showtimeid") int showtimeid, @PathVariable("emplId") String emplId,
            Model model) {
        model.addAttribute("showtimeid", showtimeid);
        model.addAttribute("emplId", emplId);
        return PATH.concat("/book-payment");
    }
}
