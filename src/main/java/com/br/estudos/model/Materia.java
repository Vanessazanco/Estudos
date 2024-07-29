package com.br.estudos.model;


@Table(name = "materias")
@Entity
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String professor;

}
