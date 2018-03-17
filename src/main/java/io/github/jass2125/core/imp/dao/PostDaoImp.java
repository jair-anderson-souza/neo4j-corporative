/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.jass2125.core.imp.dao;

import io.github.jass2125.core.client.dao.PostDao;
import io.github.jass2125.core.entity.Post;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Values;
import io.github.jass2125.core.client.service.ConnectionService;

/**
 * @author Anderson Souza <jair_anderson_bs@hotmail.com>
 * @since Mar 15, 2018 7:18:30 PM
 */
@Stateless
public class PostDaoImp implements PostDao {

    @EJB
    private ConnectionService connection;
    @Override
    public void saveRelationship(Post post) {
        Integer postId = persist(post);
        Driver driver = connection.openConnection();
        try (Session session = driver.session()) {
            StatementResult result = session.run("MATCH (U:User), (P:Post) "
                    + "WHERE ID(U) = $userid AND ID(P) = $postid "
                    + "CREATE (U)-[D:PUBLISH]->(P) "
                    + "RETURN P",
                    Values.parameters("userid", post.getUser().getId(), "postid", postId));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }
    

    @Override
    public Integer persist(Post post) {
        Driver driver = connection.openConnection();
        try (Session session = driver.session()) {
            StatementResult result = session.run("CREATE (P:Post { comment : $comment}) RETURN ID(P) as id",
                    Values.parameters("comment", post.getComment()));
            int asInt = result.single().get("id").asInt();
            return asInt;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
        return null;
    }
}
