package br.senai.sp.info.pweb.ianes.ws.rest.v1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/thaytos")
public class ThaytoController {
	
	@GetMapping
	public ResponseEntity<Object> teste() {
		
		return 
			ResponseEntity
				.status(HttpStatus.CREATED)
				.build();
	}

}
