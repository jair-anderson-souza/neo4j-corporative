/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.jass2125.core.entity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * @author Anderson Souza <jair_anderson_bs@hotmail.com>
 * @since Mar 15, 2018 1:27:41 PM
 */
@Stateless
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserPrincipal findUserByEmailAndPassword(UserPrincipal user) {
        try {
            return (UserPrincipal) em.
                    createQuery("SELECT U FROM UserPrincipal U WHERE U.account.email = :email AND U.account.password = :password").
                    setParameter("email", user.getEmail()).
                    setParameter("password", user.getPassword()).
                    getSingleResult();
        } catch (NoResultException e) {
            throw new NoUserException(e, "");
        }
    }
}
