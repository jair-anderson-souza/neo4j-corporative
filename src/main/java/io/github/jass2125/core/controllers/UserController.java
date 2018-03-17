/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.jass2125.core.controllers;

import io.github.jass2125.core.exceptions.NoUserException;
import io.github.jass2125.core.entity.Post;
import io.github.jass2125.core.client.service.PostService;
import io.github.jass2125.core.entity.User;
import io.github.jass2125.core.client.service.UserService;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    @Inject
    private Post post;
    @EJB
    private UserService userService;
    @EJB
    private PostService postService;
    @Inject
    private Map<String, Object> session;

    public UserController() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User login() {
        try {
            return userService.login(user);
        } catch (NoUserException e) {

        }
        return null;
    }

    public String redirectTo() {
        User userLogin = login();
        if (userLogin != null) {
            session.put("user", userLogin);
            return "user/home.xhtml?faces-redirect=true";
        }
        return "user/home.xhtml?faces-redirect=true";
    }

    public void publishPost() {
        User user = (User) session.get("user");
        post.setUser(user);
        postService.save(post);
    }

    public String exit() {
        session.clear();
        return "/index.xhtml?faces-redirect=true";
    }

    public List<User> getFollowers() {
        User user = (User) session.get("user");
        List<User> listFollowers = userService.loadFollowers(user);
        return listFollowers;
    }

    public List<Post> getFeed() {
        User u = (User) session.get("user");
        List<Post> listFollowers = userService.loadFeed(u);
        return listFollowers;
    }
}
