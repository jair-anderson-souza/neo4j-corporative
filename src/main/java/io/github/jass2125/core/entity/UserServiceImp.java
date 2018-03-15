/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.jass2125.core.entity;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @author Anderson Souza <jair_anderson_bs@hotmail.com>
 * @since Mar 14, 2018 4:58:22 PM
 */
@Stateless
public class UserServiceImp implements UserService {

    @EJB
    private UserDao userDao;
    @EJB
    private PasswordEncriptor passwordEncryptor;

    @Override
    public UserPrincipal login(UserPrincipal user) {
        try {
            String encryptedPassword = passwordEncryptor.encryptPassword(user.getPassword());
            user.setPassword(encryptedPassword);
            return userDao.findUserByEmailAndPassword(user);
        } catch (NoUserException e) {
            throw e;
        }
    }

}
