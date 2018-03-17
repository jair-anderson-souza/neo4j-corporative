/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.jass2125.core.imp.services;

import javax.ejb.Stateless;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import io.github.jass2125.core.client.service.ConnectionService;

/**
 * @author Anderson Souza <jair_anderson_bs@hotmail.com>
 * @since Mar 15, 2018 4:20:33 PM
 */
@Stateless
public class ConnectionServiceImp implements ConnectionService {

    @Override
    public Driver openConnection() {
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "123"));
        return driver;
//        try (Session session = driver.session()) {
//            //            StatementResult result =
//            StatementResult result = session.
//                    run("CREATE (p:Produto{codigo : $codigo, descricao : $descricao, preco : $preco}) RETURN id(p) as id",
//                            Values.parameters("codigo", 2, "descricao", "Feijão", "preco", 5.50f));
//            System.out.println(result.single().get("id"));
//        } finally {
//            driver.close();
    }
}
