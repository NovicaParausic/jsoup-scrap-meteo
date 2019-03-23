package com.scrap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scrap.entity.Limit;
import com.scrap.service.LimitService;

@RestController
@RequestMapping(value = "/api/limit")
public class LimitController {

	@Autowired
	private LimitService limitService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Limit> getLimit(@PathVariable Long id) {
		Limit ret = limitService.getLimit(id);
		
		if(ret == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(ret);
		}
	}
	
	@PostMapping
	public ResponseEntity<Limit> saveLimit(@RequestBody Limit limit) {
		Limit ret = limitService.getLimit(limit.getId());
		
		if(ret != null) {
			return ResponseEntity.unprocessableEntity().build();
		} else {
			return ResponseEntity.ok(ret);
		}
				
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deleteLimit(@PathVariable Long id){
		Limit ret = limitService.getLimit(id);
		
		if(ret == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok().build();
		}
	}
	
}
