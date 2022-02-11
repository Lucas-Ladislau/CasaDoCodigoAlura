package br.com.casadocodigo.services;

import br.com.casadocodigo.models.Pagamento;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import java.io.Serializable;
import java.math.BigDecimal;

public class PagamentoGateway implements Serializable {

    private static final long serialVersionUID = 1L;

    public String pagar(BigDecimal valorTotal) {
        Pagamento pagamento = new Pagamento(valorTotal);
        String target = "http://book-payment.herokuapp.com/payment";
        Client client = ClientBuilder.newClient();
        return client.target(target).request()
                .post(Entity.json(pagamento), String.class);
    }
}