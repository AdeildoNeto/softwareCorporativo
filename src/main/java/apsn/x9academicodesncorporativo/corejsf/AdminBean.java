/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apsn.x9academicodesncorporativo.corejsf;

import apsn.x9academicodesncorporativo.entities.Usuario;
import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author aldo_neto
 */
//@Named(value = "admin")
@RequestScoped
@ManagedBean(name= "admin")
public class AdminBean implements Serializable {

    public static Logger logger;
    
    private Usuario usuario;
    
  public void mostrarUsuario()
  {
      logger.info(usuario.getNome());
  }
   
    public String getNome() {
        return usuario.getNome();
    }

    public void setNome(String nome) {
         usuario.setNome(nome);
    }
}
