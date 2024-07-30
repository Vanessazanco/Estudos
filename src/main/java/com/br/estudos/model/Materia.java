package com.br.estudos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "materias")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeMateria;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;
}
