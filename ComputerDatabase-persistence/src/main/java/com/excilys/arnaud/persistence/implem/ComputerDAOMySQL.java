package com.excilys.arnaud.persistence.implem;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.arnaud.model.Computer;
import com.excilys.arnaud.model.ComputerList;
import com.excilys.arnaud.persistence.ComputerDAO;
import com.excilys.arnaud.persistence.exception.DAOException;

@Transactional("txManager")
@Repository
public class ComputerDAOMySQL implements ComputerDAO {
  private static final Logger logger = LoggerFactory.getLogger(ComputerDAOMySQL.class);

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public long add(Computer computer) throws DAOException {

    if (computer != null) {

      entityManager.persist(computer);
      entityManager.flush();
      entityManager.refresh(computer);
      logger.info("Création de l'ordinateur {} en base", computer.getId());
      return computer.getId();

    }

    return -1;
  }

  @Override
  public boolean update(Computer computer) throws DAOException {

    if (computer != null) {
      CriteriaBuilder cb = entityManager.getCriteriaBuilder();
      CriteriaUpdate<Computer> cd = cb.createCriteriaUpdate(Computer.class);
      Root<Computer> root = cd.from(Computer.class);
      cd.set(root.get("name"), computer.getName());
      cd.set(root.get("introduced"), computer.getIntroduced());
      cd.set(root.get("discontinued"), computer.getDiscontinued());
      cd.set(root.get("company"), computer.getCompany());
      cd.where(cb.equal(root.get("id"), computer.getId()));
      int result = entityManager.createQuery(cd).executeUpdate();
      if (result == 1) {
        logger.info("Computer {} updated", computer.getId());
        return true;
      } else {
        logger.info("Computer {} not updated", computer.getId());
        return false;
      }
    }
    return false;
  }

  @Override
  public boolean del(long id) throws DAOException {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaDelete<Computer> cd = cb.createCriteriaDelete(Computer.class);
    Root<Computer> root = cd.from(Computer.class);
    cd.where(cb.equal(root.get("id"), id));
    int result = entityManager.createQuery(cd).executeUpdate();
    if (result == 1) {
      logger.info("Computer {} deleted", id);
      return true;
    } else {
      logger.info("Computer {} not deleted", id);
      return false;
    }

  }

  @Override
  public Optional<Computer> findById(long id) throws DAOException {
    try {
      CriteriaBuilder cb = entityManager.getCriteriaBuilder();
      CriteriaQuery<Computer> cq = cb.createQuery(Computer.class);
      Root<Computer> root = cq.from(Computer.class);
      root.fetch("company", JoinType.LEFT);

      ParameterExpression<Long> p = cb.parameter(Long.class);
      cq.select(root).where(cb.equal(root.get("id"), p));
      return Optional.ofNullable(entityManager.createQuery(cq).setParameter(p, id).getSingleResult());

    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<Computer> findByName(String name) throws DAOException {
    if (name != null) {
      try {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Computer> cq = cb.createQuery(Computer.class);
        Root<Computer> root = cq.from(Computer.class);
        root.fetch("company", JoinType.LEFT);
        ParameterExpression<String> p = cb.parameter(String.class);
        cq.select(root).where(cb.equal(root.get("name"), p));
        return Optional.ofNullable(entityManager.createQuery(cq).setParameter(p, name).getSingleResult());
      } catch (NoResultException e) {
        return Optional.empty();
      }
    }
    return Optional.empty();
  }

  @Override
  public ComputerList getComputers() throws DAOException {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Computer> cq = cb.createQuery(Computer.class);
    Root<Computer> root = cq.from(Computer.class);
    root.fetch("company", JoinType.LEFT);
    cq.select(root);

    return new ComputerList(entityManager.createQuery(cq).getResultList());
  }

  @Override
  public ComputerList getNComputers(int begin, int nbComputer, int orderBy) throws DAOException {
    ComputerAtrribute[] attributes = ComputerAtrribute.values();

    if (begin >= 0 && nbComputer > 0) {
      CriteriaBuilder cb = entityManager.getCriteriaBuilder();
      CriteriaQuery<Computer> cq = cb.createQuery(Computer.class);
      Root<Computer> root = cq.from(Computer.class);
      root.fetch("company", JoinType.LEFT);
      if (orderBy == 4) {
        cq.orderBy(cb.asc(root.get("company").get("name")));

      } else if (orderBy >= 0 && orderBy < attributes.length) {
        cq.orderBy(cb.asc(root.get(attributes[orderBy].getName())));
      } else {
        cq.orderBy(cb.asc(root.get(attributes[0].getName())));
      }

      return new ComputerList(
          entityManager.createQuery(cq).setFirstResult(begin).setMaxResults(nbComputer).getResultList());
    }
    return new ComputerList();
  }

  @Override
  public ComputerList getNComputers(String pattern, int begin, int nbComputer, int orderBy) throws DAOException {
    if (pattern == null || pattern.isEmpty()) {
      return getNComputers(begin, nbComputer, orderBy);
    }
    ComputerAtrribute[] attributes = ComputerAtrribute.values();
    if (begin >= 0 && nbComputer > 0) {
      CriteriaBuilder cb = entityManager.getCriteriaBuilder();
      CriteriaQuery<Computer> cq = cb.createQuery(Computer.class);
      Root<Computer> root = cq.from(Computer.class);
      root.fetch("company", JoinType.LEFT);
      Predicate l1 = cb.like(root.get("name"), pattern + "%");
      Predicate l2 = cb.like(root.get("company").get("name"), pattern + "%");
      cq.select(root).where(cb.or(l1, l2));

      if (orderBy == 4) {
        cq.orderBy(cb.asc(root.get("company").get("name")));
      } else if (orderBy >= 0 && orderBy < attributes.length) {
        cq.orderBy(cb.asc(root.get(attributes[orderBy].getName())));
      } else {
        cq.orderBy(cb.asc(root.get(attributes[0].getName())));
      }
      return new ComputerList(
          entityManager.createQuery(cq).setFirstResult(begin).setMaxResults(nbComputer).getResultList());
    }
    return new ComputerList();
  }

  @Override
  public int getNumberOfComputer() throws DAOException {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> cq = cb.createQuery(Long.class);
    Root<Computer> root = cq.from(Computer.class);
    cq.select(cb.count(root));
    return entityManager.createQuery(cq).getSingleResult().intValue();
  }

  @Override
  public int getNumberOfComputer(String pattern) throws DAOException {
    if (pattern == null) {
      return getNumberOfComputer();
    }
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> cq = cb.createQuery(Long.class);
    Root<Computer> root = cq.from(Computer.class);
    root.join("company", JoinType.LEFT);
    Predicate l1 = cb.like(root.get("name"), pattern + "%");
    Predicate l2 = cb.like(root.get("company").get("name"), pattern + "%");
    cq.select(cb.count(root)).where(cb.or(l1, l2));
    return entityManager.createQuery(cq).getSingleResult().intValue();

  }

  @Override
  public int dels(List<Long> ids) throws DAOException {

    if (ids != null && ids.size() != 0) {
      CriteriaBuilder cb = entityManager.getCriteriaBuilder();
      CriteriaDelete<Computer> cd = cb.createCriteriaDelete(Computer.class);
      Root<Computer> root = cd.from(Computer.class);
      cd.where(root.get("id").in(ids));
      int result = entityManager.createQuery(cd).executeUpdate();
      if (result == ids.size()) {
        logger.info("Computer {} deleted", ids);
        return result;
      } else if (result <= 0) {
        logger.info("Computer {} not deleted", ids);
        return result;
      } else {
        logger.info("Some computer are deleted, the other not");
        return 0;
      }
    }
    return 0;

  }

  @Override
  public boolean delsFromCompany(long id) throws DAOException {

    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaDelete<Computer> cd = cb.createCriteriaDelete(Computer.class);
    Root<Computer> root = cd.from(Computer.class);
    cd.where(cb.equal(root.get("company").get("id"), id));
    int result = entityManager.createQuery(cd).executeUpdate();
    if (result > 0) {
      logger.info("Computers of company {} deleted", id);
      return true;
    } else {
      logger.info("Computers of company {} not deleted", id);
      return false;
    }

  }

}
