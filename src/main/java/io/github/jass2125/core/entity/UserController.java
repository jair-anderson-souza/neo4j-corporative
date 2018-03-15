/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.jass2125.core.entity;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Anderson Souza <jair_anderson_bs@hotmail.com>
 * @since Mar 14, 2018 4:45:39 PM
 */
@Named
@RequestScoped
public class UserController implements Serializable {

    @Inject
    private User user;
    @EJB
    private UserService userService;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User login() {
        try {
            return userService.login(user);
        } catch (NoUserException e) {
        }
        return null;
    }

    public String login2() {
        User login = login();
        if (login != null) {
            return "home.xhtml?faces-redirect=true";
        }
        return "index.xhtml";
    }
}
