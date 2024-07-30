package com.br.estudos.repository;

import com.br.estudos.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

}
