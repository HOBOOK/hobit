package com.hobook.hobit.controller;

import com.hobook.hobit.service.UpbitService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/markets")
    public String getTicker(@RequestParam(value = "accessKey") String accessKey,
                            @RequestParam(value = "secretKey") String secretKey){
        try{
            return upbitService.getMarkets(accessKey, secretKey);
        }catch (Exception e){
            log.error(e.getMessage());
            return e.getMessage();
        }
    }

    @GetMapping("/ticker/{markets}")
    public String getTicker(@RequestParam(value = "accessKey") String accessKey,
                            @RequestParam(value = "secretKey") String secretKey,
                            @PathVariable(value = "markets") String markets){
        try{
            return upbitService.getTicker(accessKey, secretKey, markets);
        }catch (Exception e){
            log.error(e.getMessage());
            return e.getMessage();
        }
    }
}
