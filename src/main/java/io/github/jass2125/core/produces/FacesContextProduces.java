/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.jass2125.core.produces;

import java.io.Serializable;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

/**
 * @author Anderson Souza <jair_anderson_bs@hotmail.com>
 * @since Mar 17, 2018 6:01:50 AM
 */
public class FacesContextProduces implements Serializable {

    @Produces
    private FacesContext context = FacesContext.getCurrentInstance();
}
