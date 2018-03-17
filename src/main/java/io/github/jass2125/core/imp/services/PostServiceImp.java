/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.jass2125.core.imp.services;

import io.github.jass2125.core.client.dao.PostDao;
import io.github.jass2125.core.client.service.PostService;
import io.github.jass2125.core.entity.Post;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @author Anderson Souza <jair_anderson_bs@hotmail.com>
 * @since Mar 15, 2018 7:17:38 PM
 */
@Stateless
public class PostServiceImp implements PostService {

    @EJB
    private PostDao postDao;

    @Override
    public void save(Post post) {
        try {
            postDao.saveRelationship(post);
        } catch (Exception e) {
            throw e;
        }
    }

}
