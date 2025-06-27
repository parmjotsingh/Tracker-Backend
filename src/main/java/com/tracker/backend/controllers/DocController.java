package com.tracker.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.backend.payloads.DocDto;
import com.tracker.backend.services.DocService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/doc")
public class DocController {
	@Autowired
	DocService docService;
	
	@PostMapping("/{userId}")
	public ResponseEntity<DocDto> saveDoc(@Valid @RequestBody DocDto docDto, @PathVariable(name = "userId") Integer userId) {
		System.out.println(docDto.getData());
		DocDto savedDocDto=docService.save(docDto, userId);
		return new ResponseEntity<DocDto>(savedDocDto, HttpStatus.OK);
	}
}
