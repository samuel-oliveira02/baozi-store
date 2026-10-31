package com.baozistore.baozi_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baozistore.baozi_store.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}