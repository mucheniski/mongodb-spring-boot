package com.mucheniski.mongodbspringboot.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mucheniski.mongodbspringboot.dto.UserDTO;
import com.mucheniski.mongodbspringboot.model.Post;
import com.mucheniski.mongodbspringboot.model.User;
import com.mucheniski.mongodbspringboot.service.UserService;

@RestController
@RequestMapping(value="/users")
public class UserResource {
	 
	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {	
		List<User> list = userService.findAll();
		List<UserDTO> listDTO = list.stream().map(user -> new UserDTO(user)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable String id) {	
		User user = userService.findById(id);		
		return ResponseEntity.ok().body(new UserDTO(user));
	}
	
	@GetMapping(value="/{id}/posts")
	public ResponseEntity<List<Post>> findPosts(@PathVariable String id) {	
		User user = userService.findById(id);		
		return ResponseEntity.ok().body(user.getPosts());
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody UserDTO userDTO) {	
		User user = userService.fromDTO(userDTO);
		user = userService.insert(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable String id) {	
		userService.deleteById(id);		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")	
	public ResponseEntity<User> update(@PathVariable String id, @Valid @RequestBody User updatedUser) {		
		User user = userService.update(id, updatedUser);
		return ResponseEntity.ok(user);		
	}
	
}
