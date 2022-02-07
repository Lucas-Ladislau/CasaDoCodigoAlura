package br.com.casadocodigo.models;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String titulo;

    @NotBlank
    @Length(min = 10)
    private String descricao;

    @DecimalMin("20")
    private BigDecimal preco;

    @Min(50)
    private Integer numeroPaginas;

    @Temporal(TemporalType.DATE)
    private Calendar dataPublicacao;

    private String capaPath;

    @ManyToMany
    @Size(min = 1)
    @NotNull
    private List<Autor> autores = new ArrayList<>(); //não permite que a lista de autores seja nula

    public Livro() { }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPreco() { return preco; }

    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public Integer getNumeroPaginas() { return numeroPaginas; }

    public void setNumeroPaginas(Integer numeroPaginas) { this.numeroPaginas = numeroPaginas; }

    public List<Autor> getAutores() { return autores; }

    public void setAutores(List<Autor> autores) { this.autores = autores; }

    public String getCapaPath() { return capaPath; }

    public void setCapaPath(String capaPath) { this.capaPath = capaPath; }

    public Calendar getDataPublicacao() { return dataPublicacao; }

    public void setDataPublicacao(Calendar dataPublicacao) { this.dataPublicacao = dataPublicacao; }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", numeroPaginas=" + numeroPaginas +
                ", autor(es)=" + autores +
                '}';
    }
}
