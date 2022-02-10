package br.com.casadocodigo.models;

import br.com.casadocodigo.dao.CompraDAO;
import br.com.casadocodigo.dao.LivroDAO;
import br.com.casadocodigo.dao.UsuarioDAO;
import br.com.casadocodigo.services.PagamentoGateway;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;

import java.util.List;
import java.util.Set;

@Named
@SessionScoped
//manter o carrinho do usuário até o término da sessão
public class CarrinhoCompras implements Serializable {

    private static final long serialVersionUID = 1L;

    private Set<CarrinhoItem> itens = new HashSet<>();

    @Inject
    private CompraDAO compraDao;

    //Set evita que os itens se repitam
    public void add(CarrinhoItem item){
        itens.add(item);
    }

    public List<CarrinhoItem> getItens() {
        return new ArrayList<CarrinhoItem>(itens); //poder fazer o cast do set para o list
    }

    public BigDecimal getTotal(CarrinhoItem item){
        return item.getLivro().getPreco()
                .multiply(new BigDecimal(item.getQuantidade()));
    }

    public BigDecimal getTotal(){
        BigDecimal total = BigDecimal.ZERO;
        for (CarrinhoItem carrinhoItem : itens) {
            total = total.add(carrinhoItem.getLivro().getPreco()
                    .multiply(new BigDecimal(carrinhoItem.getQuantidade())));
            //é necessário fazer o cast de Integer para BigDecimal da quantidade
        }
        return total;
    }

    public void remover(CarrinhoItem item){
        this.itens.remove(item);
    }

    public Integer getQuantidadeTotal() {
        return itens.stream().mapToInt(item -> item.getQuantidade()).sum();
    }


    public void finalizar(Compra compra) {
        compra.setItens(this.toJson());
        compra.setTotal(getTotal());
        compraDao.salvar(compra);
    }


    private String toJson() {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (CarrinhoItem item: itens) { //salva os itens como string no BD(compra)
            builder.add(Json.createObjectBuilder()
            .add("titulo", item.getLivro().getTitulo())
            .add("preço", item.getLivro().getPreco())
            .add("quantidae", item.getQuantidade())
            .add("total", getTotal(item)));
        }
        return builder.build().toString();
    }
}
