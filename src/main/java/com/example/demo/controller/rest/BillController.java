package com.example.demo.controller.rest;

import com.example.demo.dto.BillTicketDto;
import com.example.demo.dto.BillToppingDetailsDto;
import com.example.demo.entity.ToppingDetails;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.model.RateAndReviewBillModel;
import com.example.demo.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/bill")
@CrossOrigin("*")
public class BillController {
    @Autowired
    private BillService billService;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<?> getBillHistory(@RequestParam Optional<Integer> customerId) throws InvalidRequestParameterException {
        return ResponseEntity.ok(billService.getBillHistory(customerId));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> getBillDetails(@PathVariable("id") Optional<Integer> billId, @RequestParam Optional<Integer> customerId) throws InvalidRequestParameterException {
        return ResponseEntity.ok(billService.getBillDetails(billId, customerId));
    }

    @PostMapping("/ticket")
    public ResponseEntity<?> insertBill(@RequestBody Optional<BillTicketDto> billTicketDto) throws InvalidRequestParameterException {
        return ResponseEntity.ok(billService.insertBillAndTicket(billTicketDto));
    }

    @PostMapping("/updateRateAndReview")
    public ResponseEntity<?> updateRateAndReview(@RequestBody RateAndReviewBillModel model){
        return ResponseEntity.ok(billService.updateRateAndReview(model));
    }

    @GetMapping("/getByMovie")
    public ResponseEntity<?> getByMovie(@RequestParam("id") String id){
        return ResponseEntity.ok(billService.findByMovie(id));
    }

    @GetMapping("/updateExportStatus")
    public ResponseEntity<?> updateExportStatus(@RequestParam("id") int id, @RequestParam("exportstatus") boolean exportstatus){
        return ResponseEntity.ok(billService.updateExportStatus(id, exportstatus));
    }
    
    @PostMapping("/topping")
    public ResponseEntity<?> insertToppingDetailsInBill(@RequestBody Optional<BillToppingDetailsDto> billToppingDetails) throws InvalidRequestParameterException {
        return ResponseEntity.ok(billService.insertToppingDetailsInBill(billToppingDetails));
    }
}
