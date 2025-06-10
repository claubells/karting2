package com.example.peoplediscount_service.Controllers;

import com.example.peoplediscount_service.Services.PeopleDiscountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/people")
@CrossOrigin("*")
public class PeopleDiscountController {
    @Autowired
    PeopleDiscountServices peopleDiscountServices;

    // Endpoint consumido por FeignClient
    @GetMapping("/discount/{numberPeople}")
    public ResponseEntity<Double> getPeopleDiscount(@PathVariable int numberPeople) {
        Double rate = peopleDiscountServices.getPeopleDiscount(numberPeople);
        return ResponseEntity.ok(rate);
    }

}
