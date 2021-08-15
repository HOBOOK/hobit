package com.hobook.hobit.controller;

import com.hobook.hobit.service.UpbitService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
@CrossOrigin
public class UpbitController {
    private UpbitService upbitService;

    @GetMapping("/account")
    public String getAccount(@RequestParam(value = "accessKey") String accessKey, @RequestParam(value = "secretKey") String secretKey){
        try{
            return upbitService.getAccount(accessKey, secretKey);
        }catch (Exception e){
            log.error(e.getMessage());
            return e.getMessage();
        }
    }
}
