package com.br.estudos.controller;

import com.br.estudos.model.Materia;
import com.br.estudos.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("materias")
public class MateriaController {

    @Autowired
    private MateriaRepository materiaRepository;

    @PostMapping
    public void cadastrarMateria(@RequestBody Materia materia) {
        materiaRepository.save(materia);
    }

    //recupera um professor pelo ID da matéria
    @GetMapping("/materia/{id}")
    public ResponseEntity<String> listarProfessorPorID(@PathVariable Long id) {
        var materia = materiaRepository.findById(id);
        if (materia.isPresent()) {
            String professor = materia.get().getProfessor();
            return ResponseEntity.ok(professor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public Materia listarPorId(@PathVariable Long id) {
        var materia = materiaRepository.getReferenceById(id);
        return new Materia(materia);
    }

    @PutMapping("/materia/{id}")
    public ResponseEntity<Materia> atualizarMateria(@PathVariable Long id, @RequestBody Materia novosDados) {
        return materiaRepository.findById(id)
                .map(materiaExistente -> {
                    materiaExistente.setNomeMateria(novosDados.getNomeMateria());
                    materiaExistente.setProfessor(novosDados.getProfessor());
                    Materia materiaAtualizada = materiaRepository.save(materiaExistente);
                    return ResponseEntity.ok(materiaAtualizada);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}


