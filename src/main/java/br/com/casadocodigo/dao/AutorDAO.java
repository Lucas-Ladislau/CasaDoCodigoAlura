package br.com.casadocodigo.dao;

import br.com.casadocodigo.models.Autor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class AutorDAO {
    @PersistenceContext //injetar o dado
    private EntityManager em;

    public List<Autor> listar(){
        return em.createQuery("select a from Autor a", Autor.class)
                .getResultList();
    }//return uma lista dos autores existentes do BD
}
