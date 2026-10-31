package com.baozistore.baozi_store.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.baozistore.baozi_store.model.Cliente;
import com.baozistore.baozi_store.repository.ClienteRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // POST - Cadastrar cliente
    @PostMapping
    public ResponseEntity<Cliente> cadastrar(
            @Valid @RequestBody Cliente cliente) {

        cliente.setId(null);

        Cliente clienteSalvo = clienteRepository.save(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }

    // GET - Listar todos os clientes
    @GetMapping
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    // GET - Buscar cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {

        return clienteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE - Excluir cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        clienteRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}