/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.jass2125.core.imp.dao;

import io.github.jass2125.core.client.dao.UserDao;
import io.github.jass2125.core.entity.Post;
import io.github.jass2125.core.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.Values;
import io.github.jass2125.core.client.service.ConnectionService;
import io.github.jass2125.core.exceptions.NoUserException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.neo4j.driver.v1.Driver;

/**
 * @author Anderson Souza <jair_anderson_bs@hotmail.com>
 * @since Mar 15, 2018 1:27:41 PM
 */
@Stateless
public class UserDaoImp implements UserDao {

    @EJB
    private ConnectionService connection;
    private Driver driver;

    @PostConstruct
    public void init() {
        this.driver = connection.openConnection();

    }

    @PreDestroy
    @Override
    public void finalize() {
        this.connection.closeConnection(driver);
    }

    @Override
    public User findUserByEmailAndPassword(User user) {
        try (Session session = driver.session()) {
            StatementResult result = session.run("MATCH (U:User) WHERE U.email = $email AND U.password = $password RETURN ID(U) as id, U.name as name, U.email as email, U.password as password", Values.parameters("email", user.getEmail(), "password", user.getPassword()));
            if (!result.hasNext()) {
                throw new NoUserException("Login and password are wrong!!");
            } else {
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
        } catch (Exception e) {
            throw new RuntimeException("Method finduserByEmailAndPassowrd() in UserDaoImpl");
        }
    }

    @Override
    public List<User> findFollowersById(User user) {
        List<User> list = new ArrayList<>();
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
        } catch (Exception e) {
            throw new RuntimeException("Method findFollowersById() in UserDaoImpl");
        }
    }

    @Override
    public List<Post> findPostsById(User user) {
        List<Post> list = new ArrayList<>();
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
        } catch (Exception e) {
            throw new RuntimeException("Method findPostsById() in UserDaoImpl");
        }
    }

    @Override
    public List<User> findNotFollowersById(User user) {
        List<User> list = new ArrayList<>();
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
        } catch (Exception e) {
            throw new RuntimeException("Method findNotFollowersById() in UserDaoImpl");
        }
    }

    @Override
    public void save(User user) {
        try (Session session = driver.session()) {
            StatementResult result = session.run("CREATE (U:User { name : $name, email : $email, password : $password}) RETURN U", Values.parameters("name", user.getName(), "email", user.getEmail(), "password", user.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException("Method persist() in PostDaoImpl");
        }
    }

    @Override
    public void findByEmail(String email) {
        try (Session session = driver.session()) {
            StatementResult result = session.run("match(U:User) where U.email = $name RETURN U.email as email", Values.parameters("email", email));
            if (result.hasNext()) {
                throw new EmailDuplicateException("Email duplicate, type other.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Method findByEmail() in PostDaoImpl");
        }
    }
}
