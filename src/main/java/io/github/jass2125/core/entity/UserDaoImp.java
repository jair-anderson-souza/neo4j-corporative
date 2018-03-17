/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.jass2125.core.entity;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.Values;

/**
 * @author Anderson Souza <jair_anderson_bs@hotmail.com>
 * @since Mar 15, 2018 1:27:41 PM
 */
@Stateless
public class UserDaoImp implements UserDao {

//    @PersistenceContext
//    private EntityManager em;
    @EJB
    private Connection connection;

    @Override
    public User findUserByEmailAndPassword(User user) {
        Driver driver = connection.openConnection();
        try (Session session = driver.session()) {
            StatementResult result = session.run("MATCH (U:User) WHERE U.email = $email AND U.password = $password RETURN ID(U) as id, U.name as name, U.email as email, U.password as password", Values.parameters("email", user.getEmail(), "password", user.getPassword()));
            while (result.hasNext()) {
                Record record = result.next();

                Value value = record.get("id");
                Long id = value.asLong();

                value = record.get("name");
                String name = value.asString();

                value = record.get("email");
                String email = value.asString();

                value = record.get("password");
                String password = value.asString();
                return new User(id, name, email, password);
            }

        } finally {
            driver.close();
        }
        return null;
    }

    @Override
    public List<User> findFollowers(User user) {
        List<User> list = new ArrayList<>();
        Driver driver = connection.openConnection();
        try (Session session = driver.session()) {
            StatementResult result = session.run("MATCH (U2:User)-[F:FOLLOWER]->(U1:User) WHERE ID(U1) = $id  RETURN ID(U2) as id, U2.name as name, U2.email as email, U2.password as password", Values.parameters("id", user.getId()));
            while (result.hasNext()) {
                Record record = result.next();

                Value value = record.get("id");
                Long id = value.asLong();

                value = record.get("name");
                String name = value.asString();

                value = record.get("email");
                String email = value.asString();

                value = record.get("password");
                String password = value.asString();
                list.add(new User(id, name, email, password));
            }
            return list;
        }
    }

    @Override
    public List<Post> findPosts(User user) {
        List<Post> list = new ArrayList<>();
        Driver driver = connection.openConnection();
        try (Session session = driver.session()) {
            StatementResult result = session.run("MATCH (U:User)-[PUBLISH]->(P:Post) WHERE ID(U) = $id  RETURN ID(P) as id, P.comment as comment, U.name as name", Values.parameters("id", user.getId()));
            while (result.hasNext()) {
                Record record = result.next();

                Value value = record.get("id");
                Long id = value.asLong();

                value = record.get("comment");
                String comment = value.asString();

                value = record.get("name");
                String name = value.asString();

                list.add(new Post(id, comment, name));
            }
            return list;
        }
    }

    @Override
    public List<User> findNotFollowers(User user) {
        List<User> list = new ArrayList<>();
        Driver driver = connection.openConnection();
        try (Session session = driver.session()) {
            StatementResult result = session.run("MATCH NOT (U2:User)-[F:FOLLOWER]->(U1:User) WHERE ID(U1) = $id RETURN ID(U2) as id, U2.name as name, U2.email as email, U2.password as password", Values.parameters("id", user.getId()));
            while (result.hasNext()) {
                Record record = result.next();

                Value value = record.get("id");
                Long id = value.asLong();

                value = record.get("name");
                String name = value.asString();

                value = record.get("email");
                String email = value.asString();

                value = record.get("password");
                String password = value.asString();
                list.add(new User(id, name, email, password));
            }
            return list;
        }
    }
}
