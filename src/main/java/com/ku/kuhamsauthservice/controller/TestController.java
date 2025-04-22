package com.ku.kuhamsauthservice.controller;

import com.ku.kuhamsauthservice.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

//    @GetMapping
//    public ResponseEntity<?> testCall(){
//        return new ResponseEntity<>("Hello", HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<?> testCall(@RequestBody User user){
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }
}
