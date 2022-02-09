package br.com.casadocodigo.models;

import java.util.Objects;

public class CarrinhoItem {
    private Livro livro;
    private Integer quantidade;

    public CarrinhoItem(Livro livro) {
        this.livro = livro;
        this.quantidade = 1;
    }

    public Livro getLivro() { return livro; }

    public void setLivro(Livro livro) { this.livro = livro; }

    public Integer getQuantidade() { return quantidade; }

    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarrinhoItem that = (CarrinhoItem) o;
        return Objects.equals(livro, that.livro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(livro);
    }
}
