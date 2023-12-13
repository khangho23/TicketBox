package com.example.demo.controller.rest;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BillTicketDto;
import com.example.demo.dto.BillToppingDetailsDto;
import com.example.demo.enums.RequestStatusEnum;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.model.RateAndReviewBillModel;
import com.example.demo.service.BillService;
import com.example.demo.service.PusherService;

@RestController
@RequestMapping("/api/bill")
@CrossOrigin("*")
public class BillController {
    @Autowired
    private BillService billService;

    @Autowired
    private PusherService pusher;
    @GetMapping(value = {"", "/"})
    public ResponseEntity<?> getBillHistory(@RequestParam Optional<Integer> customerId) throws InvalidRequestParameterException {
        return ResponseEntity.ok(billService.getBillHistory(customerId));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> getBillDetails(@PathVariable("id") Optional<Integer> billId, @RequestParam Optional<Integer> customerId) throws InvalidRequestParameterException {
        return ResponseEntity.ok(billService.getBillDetails(billId, customerId));
    }

    @PostMapping("/ticket")
    public ResponseEntity<?> insertBillAndTicket(@RequestBody Optional<BillTicketDto> billTicketDto) throws InvalidRequestParameterException {
        pusher.realtime("seatPage-channel","seatOrder-event","Chọn vé");
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
    
    @GetMapping("/getByQR/{qrCode}")
    public ResponseEntity<?> qrResult(@PathVariable Optional<String> qrCode) throws InvalidRequestParameterException{
        return ResponseEntity.ok(billService.findBillDetailsByQrCode(qrCode));
    }

    @GetMapping("/updateExportStatus")
    public ResponseEntity<?> updateExportStatus(@RequestParam("id") Optional<Integer> id,
                                                @RequestParam("exportstatus") Optional<Integer> exportstatus) throws InvalidRequestParameterException {
        return ResponseEntity.ok(billService.updateExportStatus(id, exportstatus));
    }
    
    @PostMapping("/topping")
    public ResponseEntity<?> insertToppingDetailsInBill(@RequestBody Optional<BillToppingDetailsDto> billToppingDetails) throws InvalidRequestParameterException {
        return ResponseEntity.ok(billService.insertToppingDetailsInBill(billToppingDetails));
    }
    
    @GetMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestParam Optional<Integer> billId, @RequestParam Optional<Integer> customerId) throws InvalidRequestParameterException {
        return ResponseEntity.ok(billService.checkout(billId, customerId));
    }

    @GetMapping("/getReviewByMovieId/{id}")
	public ResponseEntity<?> getReviewByMovieId(@PathVariable("id") String id, @RequestParam Integer pageSize, @RequestParam Integer page) {
		return ResponseEntity.ok(billService.getReviewByMovieId(id, pageSize, page));
	}

    @PostMapping("/updateQrById")
    public ResponseEntity<?> updateQrById(@RequestParam Optional<Integer> billId) throws InvalidRequestParameterException {
        String qrCode = billId.get() + UUID.randomUUID().toString();
        billService.updateQrCode(billId, qrCode);
        return ResponseEntity.ok(RequestStatusEnum.SUCCESS.getResponse());
    }
    
}
