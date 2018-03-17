/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.jass2125.core.imp.services;

import io.github.jass2125.core.client.dao.UserDao;
import io.github.jass2125.core.exceptions.NoUserException;
import io.github.jass2125.core.client.service.UserService;
import io.github.jass2125.core.util.PasswordEncriptor;
import io.github.jass2125.core.entity.Post;
import io.github.jass2125.core.entity.User;
import java.util.Collections;
import java.util.List;
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
    public User login(User user) {
        try {
            String encryptedPassword = passwordEncryptor.encryptPassword(user.getPassword());
            user.setPassword(encryptedPassword);
            return userDao.findUserByEmailAndPassword(user);
        } catch (NoUserException e) {
            throw e;
        }
    }

    @Override
    public List<User> loadFollowers(User user) {
        try {
            return userDao.findFollowersById(user);
        } catch (Exception e) {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public List<Post> loadFeed(User user) {
        try {
            return userDao.findPostsById(user);
        } catch (Exception e) {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public List<User> loadNotFollowers(User user) {
        try {
            return userDao.findNotFollowersById(user);
        } catch (Exception e) {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public void register(User newUser) {
        try {
            verifyEmailDuplicate(newUser.getEmail());
            userDao.save(newUser);
        } catch (Exception e) {
            throw e;
        }
    }

    public void verifyEmailDuplicate(String email) {
        try {
            userDao.findByEmail(email);
        } catch (Exception e) {
            throw e;
        }
    }

}
