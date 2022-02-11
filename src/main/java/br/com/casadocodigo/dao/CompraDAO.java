package br.com.casadocodigo.dao;

import br.com.casadocodigo.models.Compra;
import br.com.casadocodigo.models.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

public class CompraDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    public void salvar(Compra compra) {
        em.persist(compra);
    }

    public Compra buscaPorUuid(String uuid) {
        String jpql = "SELECT c FROM Compra c WHERE c.uuid= :uuid";
        return em.createQuery(jpql, Compra.class)
                .setParameter("uuid", uuid).getSingleResult();

    }
}
