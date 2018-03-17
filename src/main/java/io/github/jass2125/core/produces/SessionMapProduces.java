package io.github.jass2125.core.produces;

import java.io.Serializable;
import java.util.Map;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @author Anderson Souza <jair_anderson_bs@hotmail.com>
 * @since Mar 17, 2018 5:28:39 AM
 */
public class SessionMapProduces implements Serializable {

    @Produces
    private Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
}
