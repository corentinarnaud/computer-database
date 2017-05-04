package com.excilys.arnaud.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.arnaud.dto.UserDto;
import com.excilys.arnaud.mapper.UserMapper;
import com.excilys.arnaud.model.User;
import com.excilys.arnaud.persistence.UserDAO;

@Service
@Transactional
public class UserService {
  
  @Autowired
  UserDAO userDAO;
  
  public boolean exist(UserDto userDto){
    return userDAO.exist(UserMapper.userDtoToUser(userDto, Arrays.asList("USER")));
  }
  
  public boolean registerNewUserAccount(UserDto userDto){
    User user = UserMapper.userDtoToUser(userDto, Arrays.asList("USER"));
    if(!userDAO.exist(user)) {
      return userDAO.add(user);
    }
    return false;
  }
  
}


