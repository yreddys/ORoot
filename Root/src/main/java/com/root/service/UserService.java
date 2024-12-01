package com.root.service;

import com.root.entity.Users;
import com.root.exception.UserAlreadyRegistered;
import com.root.exception.UserNotRegistered;
import com.root.model.UserLogin;

public interface UserService {

	Users registerUser(Users user) throws UserAlreadyRegistered;

	Users LoginUser(UserLogin login) throws UserNotRegistered;

}
