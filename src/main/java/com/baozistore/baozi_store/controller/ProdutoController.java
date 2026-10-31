package com.baozistore.baozi_store.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.baozistore.baozi_store.model.Produto;
import com.baozistore.baozi_store.repository.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // POST - Cadastrar produto
    @PostMapping
    public ResponseEntity<Produto> cadastrar(
            @Valid @RequestBody Produto produto) {

        produto.setId(null);

        Produto produtoSalvo = produtoRepository.save(produto);

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }

    // GET - Listar todos os produtos
    @GetMapping
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    // GET - Buscar produto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {

        return produtoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE - Excluir produto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        if (!produtoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        produtoRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}