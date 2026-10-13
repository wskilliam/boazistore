package com.baozi.baozistore.controller;

import com.baozi.baozistore.model.Cliente;
import com.baozi.baozistore.model.Pedido;
import com.baozi.baozistore.model.PedidoRequest;
import com.baozi.baozistore.model.Produto;
import com.baozi.baozistore.repository.ClienteRepository;
import com.baozi.baozistore.repository.PedidoRepository;
import com.baozi.baozistore.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoController(PedidoRepository pedidoRepository,
                            ClienteRepository clienteRepository,
                            ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @PostMapping
    public ResponseEntity<Pedido> criar(@RequestBody PedidoRequest req) {
        return salvar(new Pedido(), req, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoRepository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizar(@PathVariable Long id, @RequestBody PedidoRequest req) {
        Optional<Pedido> existente = pedidoRepository.findById(id);
        if (existente.isEmpty()) return ResponseEntity.notFound().build();
        return salvar(existente.get(), req, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        if (!pedidoRepository.existsById(id)) return ResponseEntity.notFound().build();
        pedidoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Pedido> salvar(Pedido pedido, PedidoRequest req, HttpStatus status) {
        if (req.clienteId() == null || req.produtoId() == null
                || req.quantidade() == null || req.quantidade() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Cliente> cliente = clienteRepository.findById(req.clienteId());
        Optional<Produto> produto = produtoRepository.findById(req.produtoId());
        if (cliente.isEmpty() || produto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        pedido.setCliente(cliente.get());
        pedido.setProduto(produto.get());
        pedido.setQuantidade(req.quantidade());
        return ResponseEntity.status(status).body(pedidoRepository.save(pedido));
    }
}
