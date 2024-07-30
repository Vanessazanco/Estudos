package com.br.estudos.controller;

import com.br.estudos.model.Materia;
import com.br.estudos.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("materias")
public class MateriaController {

    @Autowired
    private MateriaRepository materiaRepository;

    /* O método cadastrarMateria salva a nova instância de Materia no banco de dados.
    Após salvar, cria uma URI para o novo recurso usando ServletUriComponentsBuilder.
    Retorna uma resposta HTTP com status 201 (Created) e a localização do novo recurso no cabeçalho Location.
    */
    @PostMapping("/materia")
    public ResponseEntity<Void> cadastrarMateria(@RequestBody Materia materia) {
        Materia materiaSalva = materiaRepository.save(materia);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(materiaSalva.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
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

    @GetMapping("/materia/{id}")
    public ResponseEntity<Materia> listarPorId(@PathVariable Long id) {
        return materiaRepository.findById(id)
                .map(materia -> ResponseEntity.ok(materia))
                .orElseGet(() -> ResponseEntity.notFound().build());
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


