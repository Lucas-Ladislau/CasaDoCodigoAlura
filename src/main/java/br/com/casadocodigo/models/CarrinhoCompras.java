package br.com.casadocodigo.models;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
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
}
