package com.excilys.arnaud.persistence.implem;


import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.arnaud.model.User;
import com.excilys.arnaud.persistence.UserDAO;

@Transactional(transactionManager = "txManager")
@Repository
public class UserDAOMySQL implements UserDAO {
  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAOMySQL.class);

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public boolean add(User user) {
    if (user != null) {
      try {
        LOGGER.debug("try to add " + user);
        entityManager.persist(user);
        entityManager.flush();
        entityManager.refresh(user);
        LOGGER.info("Creation of {} completed", user.getName());
        return true;
      } catch (PersistenceException e) {
        LOGGER.debug("An exception during add user : " + e.getMessage());
        entityManager.clear();
        return false;
      }

    }

    return false;
  }

  @Override
  public boolean exist(User user) {
    if (user != null) {
      try {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root).where(
            cb.and(cb.equal(root.get("name"), user.getName()), cb.equal(root.get("password"), user.getPassword())));
        entityManager.createQuery(cq).getSingleResult();
        LOGGER.info("{} find", user);
        return true;
      } catch (NoResultException e) {
        LOGGER.info("{} not find", user);
        return false;
      }
    }
    LOGGER.debug("user is null");
    return false;
  }
  
  @Override
  public Optional<User> findByName(String name) {
    if (name != null) {
      try {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root).where(cb.equal(root.get("name"), name));
        User user = entityManager.createQuery(cq).getSingleResult();
        LOGGER.info("{} find", name);
        return Optional.of(user);
      } catch (NoResultException e) {
        LOGGER.info("{} not find", name);
        return Optional.empty();
      }
    }
    LOGGER.debug("user is null");
    return Optional.empty();
  }

}
