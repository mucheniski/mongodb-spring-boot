package com.mucheniski.mongodbspringboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mucheniski.mongodbspringboot.dto.UserDTO;
import com.mucheniski.mongodbspringboot.exception.ObjectNotFoundException;
import com.mucheniski.mongodbspringboot.model.User;
import com.mucheniski.mongodbspringboot.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public List<User> findAll() { 
		return userRepository.findAll();
	}	
	
	public User findById(String id) {
		Optional<User> user = userRepository.findById(id);
		return user.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado com o ID: " + id));
	}
	
	public User insert(User user) {
		return userRepository.insert(user);
	}
	
	public void deleteById(String id) {
		findById(id);
		userRepository.deleteById(id);
	}
	
	public User update(String id, User updatedUser) {
		User actualUser = findById(id);
		BeanUtils.copyProperties(updatedUser, actualUser, "id");
		return userRepository.save(actualUser);
	}
	
	public User fromDTO(UserDTO userDTO) {
		return new User(userDTO.getId(), userDTO.getName(), userDTO.getEmail());
	}
	
}
