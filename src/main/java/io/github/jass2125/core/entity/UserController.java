/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.jass2125.core.entity;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
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
    private FacesContext context;

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

    public String login2() {
        User login = login();
        if (login != null) {
            context.getExternalContext().getSessionMap().put("user", login);
//            NavigationHandler nav = context.getApplication().getNavigationHandler();
//            nav.handleNavigation(context, null, "/user/home.xhtml?faces-redirect=true");
            return "user/home.xhtml?faces-redirect=true";
        }
//        NavigationHandler nav = context.getApplication().getNavigationHandler();
//        nav.handleNavigation(context, null, "/user/home.xhtml?faces-redirect=true");
        return "user/home.xhtml?faces-redirect=true";
    }

    //pegar user da sessão
    public void publishPost() {
        User user = (User) context.getExternalContext().getSessionMap().get("user");
        post.setUser(user);
        postService.save(post);
    }

    public String exit() {
        context.getExternalContext().getSessionMap().clear();
        return "/index.xhtml?faces-redirect=true";
    }

    public List<User> getFollowers() {
        User user = (User) context.getExternalContext().getSessionMap().get("user");
        List<User> listFollowers = userService.loadFollowers(user);
        return listFollowers;
    }

//    public List<User> getNotFollowers() {
//        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
//        List<User> listFollowers = userService.loadNotFollowers(user);
//        return listFollowers;
//    }
    public List<Post> getFeed() {
        User u = (User) context.getExternalContext().getSessionMap().get("user");
        List<Post> listFollowers = userService.loadFeed(u);
        return listFollowers;
    }
}
