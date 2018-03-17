package io.github.jass2125.core.util;


import io.github.jass2125.core.client.service.ConnectionService;
import io.github.jass2125.core.imp.services.ConnectionServiceImp;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Values;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @author Anderson Souza <jair_anderson_bs@hotmail.com>
 * @since Mar 17, 2018 8:10:02 AM
 */
public class Loader {

    public static void main(String[] args) {
        ConnectionService connect = new ConnectionServiceImp();
        Driver driver = connect.openConnection();
        try (Session session = driver.session()) {
            StatementResult result1 = session.
                    run("CREATE (U:User{id : $id, name : $name, email : $email, password : $password}) RETURN U",
                            Values.parameters("id", 1, "name", "user", "email", "user@user.com", "password", "A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3"));
//            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }
}
