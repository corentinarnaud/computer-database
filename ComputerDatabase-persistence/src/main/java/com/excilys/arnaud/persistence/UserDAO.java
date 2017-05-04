package com.excilys.arnaud.persistence;



import java.util.Optional;

import com.excilys.arnaud.model.User;

public interface UserDAO {
  
  public boolean add(User user);
  
  public boolean exist(User user);

  Optional<User> findByName(String name);

}
