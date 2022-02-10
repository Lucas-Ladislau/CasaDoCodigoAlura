package br.com.casadocodigo.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.PERSIST) //salvar a compra e usuario juntos
    private Usuario usuario;

    private String uuid;

    private String itens;

    private BigDecimal total;

    //API callback do JPA
    @PrePersist //antes de persister os dados irá ser criado o uuid
    public void createUUID(){
        this.uuid = UUID.randomUUID().toString();
    }

    public String getUuid() { return uuid; }

    public void setUuid(String uuid) { this.uuid = uuid; }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }

    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getItens() { return itens; }

    public void setItens(String itens) { this.itens = itens; }

    public BigDecimal getTotal() { return total; }

    public void setTotal(BigDecimal total) { this.total = total; }
}
