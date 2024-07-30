package com.br.estudos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "materias")
@Entity
@Getter
@Setter
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeMateria;
    private String professor;

    public Materia(Materia materia) {
    }
}
