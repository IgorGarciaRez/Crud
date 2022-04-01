package com.celulaweb.crud.domain;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cidade")
@Getter
@Setter
@Builder
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @NotEmpty(message = "Não pode ser vazio")
    @Size(min = 3, max = 100)
    private String nome;

    private Integer qtdHabitantes;

    @NotNull
    @NotEmpty(message = "Não pode ser vazio")
    private String estado;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Cidade rhs = (Cidade) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(id, rhs.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).toHashCode();
    }

    public Cidade(Long id, String nome, Integer qtdHabitantes, String estado) {
        this.id = id;
        this.nome = nome;
        this.qtdHabitantes = qtdHabitantes;
        this.estado = estado;
    }

    public Cidade(){}

}