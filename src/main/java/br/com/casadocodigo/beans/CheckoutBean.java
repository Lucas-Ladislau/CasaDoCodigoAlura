package br.com.casadocodigo.beans;

import br.com.casadocodigo.dao.UsuarioDAO;
import br.com.casadocodigo.models.CarrinhoCompras;
import br.com.casadocodigo.models.Compra;
import br.com.casadocodigo.models.Usuario;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@Model
public class CheckoutBean {
    private Usuario usuario = new Usuario();

    @Inject
    private FacesContext facesContext;

    @Inject
    private CarrinhoCompras carrinho;

    @Transactional //pois irá alterar o BD
    public void finalizar(){
        Compra compra = new Compra();
        compra.setUsuario(usuario);
        carrinho.finalizar(compra);

        //definir a pagina em que o usuario sera destinado apos a finalização de cada compra
        String contextName = facesContext.getExternalContext().getRequestContextPath();
        HttpServletResponse response  = (HttpServletResponse)
                facesContext.getExternalContext().getResponse();
        response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);//redirect temporario
        response.setHeader("Location", contextName
                + "/services/pagamento?uuid="+compra.getUuid());
        //uuid faz com que o id não possa ser facilmente acessado
        //trocando apenas o seu numero na url, tornando o id
        //uma espécie de id criptografado

    }

    public Usuario getUsuario() { return usuario; }

    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
