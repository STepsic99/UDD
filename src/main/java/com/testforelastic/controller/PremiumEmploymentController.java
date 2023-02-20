package com.testforelastic.controller;

import com.testforelastic.DTO.BasicSearchDTO;
import com.testforelastic.service.GeolocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/employment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PremiumEmploymentController {

    @Autowired
    private GeolocationService geolocationService;

    @GetMapping(value = "/request")
    public void employmentRequest(HttpServletRequest request) throws IOException, InterruptedException {
        //String ipAddress = request.getRemoteAddr();
        String ipAddress = "8.10.19.99";
        String city = geolocationService.getCityBasedOnIpAddress(ipAddress);
        log.info("Employment request received from city={}",city.replaceAll("\\s+", ""));
    }

    @GetMapping(value = "/successful-employment")
    public void successfulEmployment(@RequestParam String employee, @RequestParam String company) throws IOException, InterruptedException {
        log.info("Employment at company={} was successfully led by employee={}",company.replaceAll("\\s+", ""),employee.replaceAll("\\s+", ""));
    }
}
