package br.com.casadocodigo.dao;

import br.com.casadocodigo.models.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class UsuarioDAO {

    @PersistenceContext
    private EntityManager em;

    public void salvar(Usuario usuario){
        em.persist(usuario);
    }

}
