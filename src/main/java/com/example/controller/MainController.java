package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Main", description = "Hello World")
@RestController
@RequestMapping("/")
public class MainController {

	@GetMapping
	public String helloWorld() {
		return "Hello World";
	}
}
