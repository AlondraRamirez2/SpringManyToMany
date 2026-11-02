package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Producto;
import com.example.demo.repo.productoRepo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;





@RestController
public class ProductoController {
@Autowired
productoRepo repo;
@GetMapping("productos")
  public List<Producto> findAllProductos() {
    return this.repo.findAll();
  }

@PostMapping("producto")
  public Producto addProducto(@RequestBody Producto producto) {
    return repo.save(producto);
  }

@GetMapping("producto/{id}")
public Optional<Producto> getById(@PathVariable String id) {
    return repo.findById(id);
}


@PutMapping("producto/{id}")
public Producto putProduct(@PathVariable String id, @RequestBody Producto product) {
    product.setId(id);
    return repo.save(product);
}

@DeleteMapping("producto/{id}")
public String deleteMailbox(@PathVariable("id") String id) {
    repo.deleteById(id);
    return "{\"success\":true}";
}

}

