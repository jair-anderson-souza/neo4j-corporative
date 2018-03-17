
import java.io.Serializable;
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
public class FacesContextProduces implements Serializable {

    @Produces
    private FacesContext context = FacesContext.getCurrentInstance();
}
