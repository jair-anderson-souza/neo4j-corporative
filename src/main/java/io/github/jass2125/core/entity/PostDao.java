/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.jass2125.core.entity;

/**
 * @author Anderson Souza <jair_anderson_bs@hotmail.com>
 * @since Mar 15, 2018 7:18:34 PM
 */
public interface PostDao {

    public void saveRelationship(Post post);

    public Integer persist(Post post);

}
