package com.example.karya_lab10.controller;

import com.example.karya_lab10.model.Message;
import com.example.karya_lab10.model.ResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, it works!";
    }

    @PostMapping("/hello-json")
    public ResponseEntity<ResponseMessage> helloJson(@RequestBody Message message) {

        if (message.getText() == null || message.getText().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Error: message is empty"));
        }

        ResponseMessage response =
                new ResponseMessage("Server received: " + message.getText());

        return ResponseEntity.ok(response);
    }

}
