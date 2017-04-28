package com.excilys.arnaud.persistance.implem;

import com.excilys.arnaud.model.work.Company;
import com.excilys.arnaud.model.work.CompanyList;
import com.excilys.arnaud.persistance.CompanyDAO;
import com.excilys.arnaud.persistance.exception.DAOException;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;


@Repository
public class CompanyDAOMySQL implements CompanyDAO {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAOMySQL.class);
  
  @PersistenceContext
  EntityManager entityManager;

  public CompanyDAOMySQL() {
    LOGGER.info("CompanyDao mysql instanciated");
  }

  @Override
  public Optional<Company> findById(long id) throws DAOException {
    try {
      CriteriaBuilder cb = entityManager.getCriteriaBuilder();
      CriteriaQuery<Company> cq = cb.createQuery(Company.class);
      Root<Company> root = cq.from(Company.class);
      ParameterExpression<Long> p = cb.parameter(Long.class);
      cq.select(root).where(cb.equal(root.get("id"), p));
      
      return Optional.ofNullable(entityManager.createQuery(cq).setParameter(p, id).getSingleResult());
    } catch (NoResultException e) {
      return Optional.empty();
    } 
  }

  @Override
  public Optional<Company> findByName(String name) throws DAOException {
    try {
      CriteriaBuilder cb = entityManager.getCriteriaBuilder();
      CriteriaQuery<Company> cq = cb.createQuery(Company.class);
      Root<Company> root = cq.from(Company.class);
      ParameterExpression<String> p = cb.parameter(String.class);
      cq.select(root).where(cb.equal(root.get("name"), p));
      return Optional.ofNullable(entityManager.createQuery(cq).setParameter(p, name).getSingleResult());
    } catch (NoResultException e) {
      return Optional.empty();
    } catch (DataAccessException e) {
      LOGGER.debug(e.getMessage());
      throw new DAOException(e);
    }
  }

  @Override
  public CompanyList getCompanies() throws DAOException {
    try {
      CriteriaQuery<Company> cq = entityManager.getCriteriaBuilder().createQuery(Company.class);
      Root<Company> root = cq.from(Company.class);
      cq.select(root);
      
      return new CompanyList(entityManager.createQuery(cq).getResultList());
    } catch (DataAccessException e) {
      LOGGER.debug(e.getMessage());
      throw new DAOException(e);
    }
  }

  @Override
  public CompanyList getNCompanies(int begin, int nbCompanies) throws DAOException {
    try {
      CriteriaQuery<Company> cq = entityManager.getCriteriaBuilder().createQuery(Company.class);
      Root<Company> root = cq.from(Company.class);
      cq.select(root);
      
      return new CompanyList(entityManager.createQuery(cq)
          .setFirstResult(begin).setMaxResults(nbCompanies).getResultList());
    } catch (DataAccessException e) {
      LOGGER.debug(e.getMessage());
      throw new DAOException(e);
    }
  }

  @Override
  public int getNumberOfCompany() {
    try {
      CriteriaBuilder qb = entityManager.getCriteriaBuilder();
      CriteriaQuery<Long> cq = qb.createQuery(Long.class);
      cq.select(qb.count(cq.from(Company.class)));
      
      return entityManager.createQuery(cq).getSingleResult().intValue();
    } catch (DataAccessException e) {
      LOGGER.debug(e.getMessage());
      throw new DAOException(e);
    }
  }

  @Override
  public boolean delCompany(long id) {
    try {
      CriteriaBuilder cb = entityManager.getCriteriaBuilder();

      // create delete
      CriteriaDelete<Company> delete = cb.
       createCriteriaDelete(Company.class);

      // set the root class
      Root<Company> e = delete.from(Company.class);

      // set where clause
      delete.where(cb.equal(e.get("id"), id));

      // perform update
      int resultat = entityManager.createQuery(delete).executeUpdate();

      if (resultat == 1) {
        LOGGER.info("Company {} deleted", id);
        return true;
      } else {
        LOGGER.info("Company {} not deleted", id);
        return false;
      }
    } catch (PersistenceException e) {
      LOGGER.debug(e.getMessage());
      return false;
    }
  }

}
