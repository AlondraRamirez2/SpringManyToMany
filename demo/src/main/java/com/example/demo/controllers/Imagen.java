package com.example.demo.controllers;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.model.Product;
import com.example.demo.model.Image;
import com.example.demo.repo.imagenRepo;
import com.example.demo.repo.productoRepo;

@RestController
public class Imagen {

@Autowired
imagenRepo repo;
@Autowired
productoRepo repoProducto;
@GetMapping("imagenes")
  public List<Image> findAllimagenes() {
    return this.repo.findAll();
  }

@PostMapping("imagen")
  public Image addimagen(@RequestBody Image imagen) {

    return repo.save(imagen);
  }
  @PostMapping("imagen/{productId}")
  public Image addimagen(@RequestBody Image imagen,@PathVariable String productId) {
    Optional<Product> p = repoProducto.findById(productId);
    if(p.isPresent()){
      //System.out.println(p.get().getNombre());
      imagen.setProducto(p.get());
    }
    return repo.save(imagen);

  }
  @GetMapping("imagen/{productId}")
  public Set<Image>  getImagensByProduct(@PathVariable String productId) {
    Optional<Product> p = repoProducto.findById(productId);
    if(p.isPresent()){
      return  p.get().getimagenes();
    }
    return null;

  }

@GetMapping("imagen/{id}")
public Optional<Image> getById(@PathVariable String id) {
    return repo.findById(id);
}


@PutMapping("imagen/{id}")
public Image putusuari(@PathVariable String id, @RequestBody Image imagen) {
    imagen.setId(id);
    return repo.save(imagen);
}

@DeleteMapping("imagen/{id}")
public String deleteMailbox(@PathVariable String id) {
    repo.deleteById(id);
    return "{\"success\":true}";
}
}

