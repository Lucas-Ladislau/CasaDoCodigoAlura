package br.com.casadocodigo.beans;

import br.com.casadocodigo.dao.LivroDAO;
import br.com.casadocodigo.models.Livro;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Model
public class AdminListaLivrosBean {

    @Inject
    private LivroDAO dao;

    private List<Livro> livros = new ArrayList<>();

    public List<Livro> getLivros() {
        this.livros = dao.listar();
        return livros;
    }


}
