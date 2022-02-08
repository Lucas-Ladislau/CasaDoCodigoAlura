package br.com.casadocodigo.beans;


import br.com.casadocodigo.dao.LivroDAO;
import br.com.casadocodigo.models.Livro;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class LivroDetalheBean {

    @Inject
    private LivroDAO dao;

    private Livro livro;

    private Integer id;

    public void carregarDetalhe(){
        this.livro = dao.buscaPorId(id);
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Livro getLivro() { return livro; }

    public void setLivro(Livro livro) { this.livro = livro; }



}
