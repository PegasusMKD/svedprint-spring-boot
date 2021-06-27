package com.svedprint.main.controllers;

import com.svedprint.main.services.GlobalService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class GlobalController {

	private final GlobalService globalService;

	public GlobalController(GlobalService globalService) {
		this.globalService = globalService;
	}

	@PostMapping("/api/init")
	public void initDB() throws IOException {
		globalService.initDB();
	}

}
