/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.jass2125.core.entity;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 * @author Anderson Souza <jair_anderson_bs@hotmail.com>
 * @since Mar 15, 2018 3:23:34 PM
 */
@Stateless
public class GeneratorAlgorithmCryptographyImp implements GeneratorAlgorithmCryptography {

    private MessageDigest instance;
    private StringBuilder builder;
    private byte[] digest;

    public GeneratorAlgorithmCryptographyImp() {
    }

    @Override
    public String digest(String password) {
        createEncryptionAlgorithm();
        byte[] dig = this.instance.digest(encodingInAByteSequence(password));
        setDigest(dig);
        appendHexadecimal();
        return builder.toString();
    }

    private void createEncryptionAlgorithm() {
        try {
            this.instance = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger(GeneratorAlgorithmCryptographyImp.class.getName()).log(Level.WARNING, "Não foi possível criar o algoritmo de criptografia SHA-256", e);
            throw new CryptographyException(e, "Não foi possível criar o algoritmo de criptografia especificado");
        }
    }

    private byte[] encodingInAByteSequence(String password) {
        try {
            return password.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            Logger.getLogger(GeneratorAlgorithmCryptographyImp.class.getName()).log(Level.WARNING, "Não foi possível codificar a senha!", e);
            throw new EncodingException(e, "Não foi possível realizar a codificação da senha!");
        }
    }

    private void setDigest(byte[] digest) {
        this.digest = digest;
    }

    public void appendHexadecimal() {
        createBuilder();
        builder = new StringBuilder();
        for (byte b : digest) {
            builder.append(formatInHexadecimal(b));
        }
    }

    public void createBuilder() {
        this.builder = new StringBuilder();
    }

    private String formatInHexadecimal(byte b) {
        return String.format("%02X", 0xFF & b);
    }

}
