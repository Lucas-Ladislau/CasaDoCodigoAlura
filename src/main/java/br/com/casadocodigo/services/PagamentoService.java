package br.com.casadocodigo.services;

import br.com.casadocodigo.dao.CompraDAO;
import br.com.casadocodigo.models.Compra;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Path("/pagamento")
public class PagamentoService {

    @Inject
    private CompraDAO compraDao;

    @Inject
    private PagamentoGateway pagamentoGateway;

    @Context
    private ServletContext context;

    //Fazendo a requisição assincronamente
    private static ExecutorService executor = Executors.newFixedThreadPool(50);

    @POST//metodo so recebe a requisição do tipo post
    public void pagar(@Suspended final AsyncResponse ar, @QueryParam("uuid") String uuid){
        Compra compra = compraDao.buscaPorUuid(uuid);
        //lambda para execução assincrona
        executor.submit(()-> {
            try {
                String resposta = pagamentoGateway.pagar(compra.getTotal());
                System.out.println(resposta);

                URI responseUri= UriBuilder.fromPath("http://localhost:8080"+
                        context.getContextPath() + "/index.xhtml")
                        .queryParam("msg","Compra Realizada com Sucesso!")
                        .build();
                Response response = Response.seeOther(responseUri).build();
                ar.resume(response);
            }catch (Exception e){
                ar.resume(new WebApplicationException(e));
            }
        });

    }
}
