package br.com.casadocodigo.beans;

import br.com.casadocodigo.dao.LivroDAO;
import br.com.casadocodigo.models.CarrinhoCompras;
import br.com.casadocodigo.models.CarrinhoItem;
import br.com.casadocodigo.models.Livro;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.List;

@Model
public class CarrinhoComprasBean {
    @Inject
    private LivroDAO dao;

    @Inject
    private CarrinhoCompras carrinho;

    public String add(Integer id){
        Livro livro = dao.buscaPorId(id);
        CarrinhoItem item = new CarrinhoItem(livro);
        carrinho.add(item);

        return "carrinho?faces-redirect=true";
    }

    public List<CarrinhoItem> getItens(){
        return carrinho.getItens();
    }

    public void remover(CarrinhoItem item){
        carrinho.remover(item);
    }
}
