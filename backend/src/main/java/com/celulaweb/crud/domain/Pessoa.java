package com.celulaweb.crud.domain;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "pessoa")
@Getter
@Setter
@Builder
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @NotEmpty(message = "Não pode ser vazio")
    @Size(min = 3, max = 100)
    private String nome;

    @NotNull
    @NotEmpty(message = "Não pode ser vazio")
    @Size(min = 11, max = 11)
    private String cpf;

    private String apelido;
    private String timeCoracao;
    private String hobbie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;

    public Pessoa() {}

    public Pessoa(Long id, String nome, String cpf, String apelido, String timeCoracao, String hobbie, Cidade cidade) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.apelido = apelido;
        this.timeCoracao = timeCoracao;
        this.hobbie = hobbie;
        this.cidade = cidade;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Pessoa rhs = (Pessoa) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(id, rhs.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).toHashCode();
    }
}
