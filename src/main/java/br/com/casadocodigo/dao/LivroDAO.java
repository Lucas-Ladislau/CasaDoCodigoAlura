package br.com.casadocodigo.dao;

import br.com.casadocodigo.models.Livro;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

@Stateful
public class LivroDAO {
    @PersistenceContext(type = PersistenceContextType.EXTENDED) //Persistencia dos dados
    private EntityManager em; //EXTENDED é para que a requisição do entity manager dure todo o escopo
                              //ou seja, até o fim da requisição dos dados, mas esta restrito ao uso do stateful

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
                .setFirstResult(5).getResultList();
    }

    public Livro buscaPorId(Integer id) {
 //       return em.find(Livro.class, id);//faz o uso do extended e statetul
        //porem este faz uso de 2 selects enquanto o de baixo apenas 1
        //também conhecida como query planejada

//Outra forma de fazer sem o extended e o stateful
       String jpql = "SELECT l FROM Livro l JOIN FETCH l.autores"
               +" WHERE l.id = :id"; //para a chamada de autores integrados aos seus livros para o find
       return em.createQuery(jpql, Livro.class)
                .setParameter("id",id).getSingleResult();
    }
    //sem ele se o livro tivesse vários autores ele iria repetir o mesmo
    //livro para cada autor
    
}
