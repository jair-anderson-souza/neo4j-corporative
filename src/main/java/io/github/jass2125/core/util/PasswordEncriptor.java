/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.jass2125.core.util;

/**
 * @author Anderson Souza <jair_anderson_bs@hotmail.com>
 * @since Mar 15, 2018 3:25:49 PM
 */
public interface PasswordEncriptor {

    public String encryptPassword(String password);

    public boolean comparatePassword(String passwordUser, String passwordForm);
}
