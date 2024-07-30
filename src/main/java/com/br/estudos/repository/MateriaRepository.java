package com.br.estudos.repository;

import com.br.estudos.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MateriaRepository  extends JpaRepository<Materia,Long> {
    Optional<Materia> findByNomeMateria(String nomeMateria);
}
