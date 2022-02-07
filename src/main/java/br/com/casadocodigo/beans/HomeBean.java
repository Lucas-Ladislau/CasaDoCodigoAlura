package br.com.casadocodigo.beans;

import br.com.casadocodigo.dao.LivroDAO;
import br.com.casadocodigo.models.Livro;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.List;

@Model
public class HomeBean {

    @Inject
    private LivroDAO dao;

    public List<Livro> ultimosLancamentos(){
        return dao.ultimosLancamentos();
    }

    public List<Livro> demaisLivros(){
        return dao.demaisLivros();
    }
}
