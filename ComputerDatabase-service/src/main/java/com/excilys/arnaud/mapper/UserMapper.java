package com.excilys.arnaud.mapper;

import java.util.List;

import com.excilys.arnaud.dto.UserDto;
import com.excilys.arnaud.model.User;

public class UserMapper {

  public final static User userDtoToUser(UserDto userDto, List<String> roles){
    return new User(userDto.getName(), userDto.getPassword(), roles);
  }
}
