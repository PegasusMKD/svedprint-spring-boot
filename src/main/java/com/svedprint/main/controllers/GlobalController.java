package com.svedprint.main.controllers;

import com.svedprint.main.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class GlobalController {

    @Autowired
    private GlobalService globalService;

    @PostMapping("/api/init")
    public void initDB() throws IOException {
        globalService.initDB();
    }

}
