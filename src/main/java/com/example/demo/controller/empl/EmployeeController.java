package com.example.demo.controller.empl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.BillDetailsDto;
import com.example.demo.dto.BillDto;
import com.example.demo.entity.Bill;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.service.BillService;

@Controller
@RequestMapping("/empl")
public class EmployeeController {
	@Autowired
	BillService billService;
	
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
    
    @GetMapping(value = {"/book-topping_{showtimeid}_{emplId}"})
    public String book_topping(@PathVariable("showtimeid") int showtimeid, @PathVariable("emplId") String emplId,Model model) {
    	model.addAttribute("showtimeid",showtimeid);
    	model.addAttribute("emplId",emplId);
    	return PATH.concat("/book-topping");
    }
    
    @GetMapping("/scan-ticket")
    public String scan_ticket(){
        return PATH.concat("/scan-ticket");
    }
}
