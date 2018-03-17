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
import javax.annotation.PostConstruct;
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
    private User userOn;

    public UserController() {
    }

    @PostConstruct
    public void init() {
        this.userOn = (User) session.get("user");
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
        post.setUser(userOn);
        postService.save(post);
    }

    public String exit() {
        session.clear();
        return "/index.xhtml?faces-redirect=true";
    }

    public List<User> getFollowers() {
        return userService.loadFollowers(userOn);
    }

    public List<Post> getMyPosts() {
        return userService.loadFeed(userOn);
    }
}
