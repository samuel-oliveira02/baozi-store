package com.baozistore.baozi_store.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.baozistore.baozi_store.dto.PedidoRequest;
import com.baozistore.baozi_store.model.Cliente;
import com.baozistore.baozi_store.model.Produto;
import com.baozistore.baozi_store.model.Pedido;
import com.baozistore.baozi_store.repository.ClienteRepository;
import com.baozistore.baozi_store.repository.ProdutoRepository;
import com.baozistore.baozi_store.repository.PedidoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoController(
            PedidoRepository pedidoRepository,
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository) {

        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    // POST - Registrar pedido
    @PostMapping
    public ResponseEntity<?> cadastrar(
            @Valid @RequestBody PedidoRequest dados) {

        // Verificar se o cliente existe
        Cliente cliente = clienteRepository.findById(dados.getClienteId())
                .orElse(null);

        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }

        // Verificar se o produto existe
        Produto produto = produtoRepository.findById(dados.getProdutoId())
                .orElse(null);

        if (produto == null) {
            return ResponseEntity.notFound().build();
        }

        // Verificar disponibilidade do produto
        if (!Boolean.TRUE.equals(produto.getEstoque())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Produto indisponível em estoque.");
        }

        // Criar o pedido
        Pedido pedido = new Pedido();

        pedido.setCliente(cliente);
        pedido.setProduto(produto);
        pedido.setQuantidade(dados.getQuantidade());

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoSalvo);
    }

    // GET - Listar todos os pedidos
    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    // GET - Buscar pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {

        return pedidoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE - Excluir pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        if (!pedidoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        pedidoRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}