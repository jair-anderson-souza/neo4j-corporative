/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.jass2125.core.entity;

import java.util.List;

/**
 * @author Anderson Souza <jair_anderson_bs@hotmail.com>
 * @since Mar 15, 2018 3:46:14 PM
 */
public interface UserDao {

    public User findUserByEmailAndPassword(User user);

    public List<User> findFollowers(User user);
}
