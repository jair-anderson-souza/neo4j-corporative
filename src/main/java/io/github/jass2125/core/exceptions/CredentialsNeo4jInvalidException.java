/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.jass2125.core.exceptions;

import org.neo4j.driver.v1.exceptions.AuthenticationException;

/**
 * @author Anderson Souza <jair_anderson_bs@hotmail.com>
 * @since Mar 17, 2018 6:38:48 AM
 */
public class CredentialsNeo4jInvalidException extends RuntimeException {

    public CredentialsNeo4jInvalidException(String msg, AuthenticationException e) {
        super(msg, e);
    }

}
