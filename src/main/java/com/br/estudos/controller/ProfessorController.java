package com.br.estudos.controller;

import com.br.estudos.model.Professor;
import com.br.estudos.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/professores")
public class ProfessorController {
    @Autowired
    private ProfessorRepository professorRepository;

    @PostMapping
    public ResponseEntity<Void> cadastrarProfessor(@RequestBody Professor professor) {
        Professor professorSalvo = professorRepository.save(professor);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(professorSalvo.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> listarPorId(@PathVariable Long id) {
        return professorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> atualizarProfessor(@PathVariable Long id, @RequestBody Professor novosDados) {
        return professorRepository.findById(id)
                .map(professorExistente -> {
                    professorExistente.setNome(novosDados.getNome());
                    professorExistente.setMateria(novosDados.getMateria());
                    Professor professorAtualizado = professorRepository.save(professorExistente);
                    return ResponseEntity.ok(professorAtualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarProfessor(@PathVariable Long id) {
        return professorRepository.findById(id)
                .map(professor -> {
                    professorRepository.delete(professor);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
