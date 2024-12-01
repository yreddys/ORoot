package com.root.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.root.entity.Users;
import com.root.exception.UserAlreadyRegistered;
import com.root.exception.UserNotRegistered;
import com.root.model.UserLogin;
import com.root.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public Users registerUser(Users user) throws UserAlreadyRegistered {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new UserAlreadyRegistered("User with email " + user.getEmail() + " is already registered.");

		}
		return userRepository.save(user);
	}

	@Override
	public Users LoginUser(UserLogin login) throws UserNotRegistered {
		Users user = new Users();
		user = userRepository.findByEmail(login.getEmail());
		if (user == null) {
			// Throw an exception if the user is not found
			throw new UserNotRegistered("User with email " + login.getEmail() + " is not registered.");
		}
		return user;
	}

}
