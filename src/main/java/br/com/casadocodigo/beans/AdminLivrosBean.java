package br.com.casadocodigo.beans;

import br.com.casadocodigo.dao.AutorDAO;
import br.com.casadocodigo.dao.LivroDAO;
import br.com.casadocodigo.models.Autor;
import br.com.casadocodigo.models.Livro;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

//para ligar com a view é necessário o CDI
@Named
@RequestScoped //para manter os dados durante o tempo de execução
public class AdminLivrosBean {
    //adminLivrosBean = AdminLivrosController

    private Livro livro = new Livro();

    @Inject
    private LivroDAO dao;
    @Inject
    private AutorDAO autorDAO; //para fazer operações com  o autor no bd

    @Inject
    private FacesContext context;


    @Transactional //necessário a anotação para a transição/alteração no DB
    public String salvar(){ //separa os autores da lista para 1 unico objetp autorID
        dao.salvar(livro);

        //.getExternalContext().getFlash(). vai fazer com que a mensagem perdure por 2 requests
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context
                .addMessage(null, new FacesMessage("Livro cadastrado com sucesso"));
        System.out.println("Livro Cadastrado: " + livro);

        return "/livro/lista?faces-redirect=true";//direciona o usuário para a lista de livros
        //?faces-redirect=true impede que ao atualizar a página seja feito o reenvio do formulário ao BD
//        this.livro = new Livro(); limpar o campos para um novo cadastro
//        this.autoresId = new ArrayList<>(); limpar a seleção anterior dos autores
    }

    public List<Autor> getAutores(){
        return autorDAO.listar();
    }

    public Livro getLivro() { return livro; }

    public void setLivro(Livro livro) { this.livro = livro; }

}
