package com.test_jav.test_jav.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.test_jav.test_jav.model.Dependente;
import com.test_jav.test_jav.service.DependenteService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/dependentes")
public class DependenteController {

    private DependenteService dependenteService;

    // CRIA NOVO DEPENDENTE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dependente postDependente(@RequestBody @Valid Dependente dependente) {
        return dependenteService.salvar(dependente);
    }

    // RETORNA TODOS OS DEPENDENTES
    @GetMapping
    public List<Dependente> getAllDependentes() {
        return dependenteService.findAll();
    }

    // RETORNA DEPENDENTE ESPECÍFICO PELO ID
    @GetMapping("/{depId}")
    public ResponseEntity<Dependente> getOneDependente(@PathVariable Long depId) {
        Optional<Dependente> dependenteOpt = dependenteService.findById(depId);

        return dependenteOpt.isPresent()
                ? ResponseEntity.ok(dependenteOpt.get())
                : ResponseEntity.notFound().build();
    }

    // MODIFICA DEPENDENTE ESPECÍFICO PELO ID
    @PutMapping("/{depId}")
    public ResponseEntity<Dependente> updateDependente(@PathVariable Long depId,
            @RequestBody @Valid Dependente dependente) {
        if (!dependenteService.existsById(depId)) {
            return ResponseEntity.notFound().build();
        }

        dependente.setId(depId);
        dependente = dependenteService.salvar(dependente);
        return ResponseEntity.ok(dependente);
    }

    // EXCLUI DEPENDENTE ESPECÍFICO PELO ID
    @DeleteMapping("/{depId}")
    public ResponseEntity<Void> deleteDependente(@PathVariable Long depId) {
        if (!dependenteService.existsById(depId)) {
            return ResponseEntity.notFound().build();
        }

        dependenteService.excluir(depId);
        return ResponseEntity.noContent().build();
    }
}