package br.com.casadocodigo.dao;

import br.com.casadocodigo.models.Livro;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class LivroDAO {
    @PersistenceContext //Persistencia dos dados
    private EntityManager em;

    public void salvar(Livro livro){
        //O JTA resolve a questao das transções de begin e commit com o banco
        em.persist(livro);
    }

    //Listar todos os livros
    public List<Livro> listar() {
        String jpql = "SELECT DISTINCT (l) FROM Livro l"
                + " JOIN FETCH l.autores "; //Juntar o livro relacionado ao seu respectivo autor(res)
        return em.createQuery(jpql, Livro.class).getResultList();
    }//DISTINCT serve para trazer somente 1 livro para cada autor(res),

    public List<Livro> ultimosLancamentos() {
        String jpql = "SELECT l FROM Livro l ORDER BY l.id DESC";
        return em.createQuery(jpql, Livro.class)
                .setMaxResults(5).getResultList();
    }//Traz a consulta dos ultimos livros

    public List<Livro> demaisLivros() {
        String jpql = "SELECT l FROM Livro l ORDER BY l.id DESC";
        return em.createQuery(jpql, Livro.class)
                .setFirstResult(6).getResultList();
    }
    //sem ele se o livro tivesse vários autores ele iria repetir o mesmo
     //livro para cada autor
    
}
