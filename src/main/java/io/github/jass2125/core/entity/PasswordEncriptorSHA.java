/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.jass2125.core.entity;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @author Anderson Souza <jair_anderson_bs@hotmail.com>
 * @since Mar 15, 2018 3:25:27 PM
 */
@Stateless
public class PasswordEncriptorSHA implements PasswordEncriptor {

    @EJB
    private GeneratorAlgorithmCryptography cryptographer;
    private String passwordToCryptography;
    private String passwordEncrypted;

    public PasswordEncriptorSHA() {
    }
//    public PasswordEncriptorSHA() {
//        this.cryptographer = new GeneratorAlgorithmCryptographyImp();
//    }

    public void setPasswordEncrypted(String passwordEncrypted) {
        this.passwordEncrypted = passwordEncrypted;
    }

    @Override
    public String encryptPassword(String password) {
        this.setPasswordToCryptography(this.cryptographer.digest(password));
        return this.passwordToCryptography;
    }

    public void setPasswordToCryptography(String passwordToCryptography) {
        this.passwordToCryptography = passwordToCryptography;
    }

    @Override
    public boolean comparatePassword(String passwordUser, String passwordForm) {
        setPasswordEncrypted(passwordUser);
        String passwordInHash = cryptographer.digest(passwordForm);
        setPasswordToCryptography(passwordInHash);
        return this.verifyIfPasswordIsEquals();
    }

    private boolean verifyIfPasswordIsEquals() {
        return passwordToCryptography.equals(passwordEncrypted);
    }

}
