package br.com.casadocodigo.dao;

import br.com.casadocodigo.models.Livro;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.QueryHints;

import javax.ejb.Stateful;
import javax.persistence.Cache;
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
                .setMaxResults(5)
                .setHint(QueryHints.HINT_CACHEABLE,true)
                .setHint(QueryHints.HINT_CACHE_REGION, "home")
                .getResultList();
    }//Traz a consulta dos ultimos livros
//  .setHint() armazena as informações em cache da memória para serem acessadas
//mais rapidamente
//    hint cache region defini uma região de cache


    public List<Livro> demaisLivros() {
        String jpql = "SELECT l FROM Livro l ORDER BY l.id DESC";
        return em.createQuery(jpql, Livro.class)
                .setFirstResult(5)
                .setHint(QueryHints.HINT_CACHEABLE,true)
                .setHint(QueryHints.HINT_CACHE_REGION, "home")
                .getResultList();
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

    public void LimpaCache(){
        Cache cache = em.getEntityManagerFactory().getCache();
        cache.evict(Livro.class, 1l); //limpar o cache do livro com id 1
        cache.evictAll();//evita o cache de todas as classes;

        SessionFactory factory = em.getEntityManagerFactory().unwrap(SessionFactory.class);
        factory.getCache().evictAllRegions(); //evita o cache de todas as regioes defindas naquela pagina
        factory.getCache().evictQueryRegion("home");// evita o cash da região definida(nesse caso é home"
    }
    //TOMAR CUIDADO COM O USO DO TEMPO DE CASH ATIVO, POIS SE UM ELEMENTO FOR DELETADO DO BD
    //ELE CONTINUARA SENDO EXIBIDO ATÉ O TEMPO FINAL DO CASH
    //infispan é uma propriedade do hibernate
}
