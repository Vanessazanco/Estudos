package com.br.estudos.controller;

import com.br.estudos.model.DTO.AtualizarProfessorDTO;
import com.br.estudos.model.Materia;
import com.br.estudos.model.DTO.MateriaDTO;
import com.br.estudos.model.Professor;
import com.br.estudos.repository.MateriaRepository;
import com.br.estudos.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.Optional;

import java.net.URI;

@RestController
@RequestMapping("/materia")
public class MateriaController {

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private final ProfessorRepository professorRepository;

    public MateriaController(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    /* O método cadastrarMateria salva a nova instância de Materia no banco de dados.
    Após salvar, cria uma URI para o novo recurso usando ServletUriComponentsBuilder.
    Retorna uma resposta HTTP com status 201 (Created) e a localização do novo recurso no cabeçalho Location.
    */
    @PostMapping
    public ResponseEntity<Void> cadastrarMateria(@RequestBody MateriaDTO materiaDTO) {
        Optional<Professor> professorOptional = professorRepository.findById(materiaDTO.getProfessorId());
        if (!professorOptional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        Materia materia = new Materia();
        materia.setNomeMateria(materiaDTO.getNomeMateria());
        materia.setProfessor(professorOptional.get());

        Materia materiaSalva = materiaRepository.save(materia);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(materiaSalva.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Materia> listarPorId(@PathVariable Long id) {
        Optional<Materia> materia = materiaRepository.findById(id);
        return materia.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Materia> atualizarProfessorDaMateria(@PathVariable Long id, @RequestBody AtualizarProfessorDTO atualizarProfessorDTO) {
        return materiaRepository.findById(id)
                .map(materiaExistente -> {
                    return professorRepository.findById(atualizarProfessorDTO.getProfessorId())
                            .map(professor -> {
                                materiaExistente.setProfessor(professor);
                                Materia materiaAtualizada = materiaRepository.save(materiaExistente);
                                return ResponseEntity.ok(materiaAtualizada);
                            })
                            .orElseGet(() -> ResponseEntity.badRequest().build());
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Busca professor por ID da matéria
    @GetMapping("/professor/id/{id}")
    public ResponseEntity<String> buscarProfessorPorId(@PathVariable Long id) {
        Optional<Materia> materia = materiaRepository.findById(id);
        if (materia.isPresent()) {
            Professor professor = materia.get().getProfessor();
            if (professor != null) {
                return ResponseEntity.ok(professor.getNome());
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Busca professor pelo nome da matéria
    @GetMapping("/professor/nome/{nome}")
    public ResponseEntity<String> buscarProfessorPorNome(@PathVariable String nome) {
        Optional<Materia> materia = materiaRepository.findByNomeMateria(nome);
        if (materia.isPresent()) {
            Professor professor = materia.get().getProfessor();
            if (professor != null) {
                return ResponseEntity.ok(professor.getNome());
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}


