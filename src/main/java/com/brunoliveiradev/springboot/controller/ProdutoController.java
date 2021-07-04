package com.brunoliveiradev.springboot.controller;

import com.brunoliveiradev.springboot.model.ProdutoModel;
import com.brunoliveiradev.springboot.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping
    public ResponseEntity<List<ProdutoModel>> getAllProdutos(){
        //findAll = retorna uma lista com todos
        List<ProdutoModel> produtosList = produtoRepository.findAll();

        if (produtosList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(produtosList, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoModel> getOneProduto(@PathVariable(value = "id") Long id){

        Optional<ProdutoModel> produtoOpt = produtoRepository.findById(id);
        // If a value is present, returns an Optional, orElse 'not found'
        return produtoOpt.map(produtoModel -> new ResponseEntity<>(produtoModel, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ProdutoModel> saveProduto(@RequestBody @Valid ProdutoModel produto){
        return new ResponseEntity<>(produtoRepository.save(produto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduto(@PathVariable(value = "id") Long id){
        Optional<ProdutoModel> produtoOpt = produtoRepository.findById(id);

        if (produtoOpt.isPresent()){
            produtoRepository.delete(produtoOpt.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoModel> updateProduto(@PathVariable(value = "id") Long id,
                                                      @RequestBody @Valid ProdutoModel produto) {

        Optional<ProdutoModel> produtoOpt = produtoRepository.findById(id);
        if (produtoOpt.isPresent()){
            produto.setIdProduto(produtoOpt.get().getIdProduto());
            return new ResponseEntity<>(produtoRepository.save(produto), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
