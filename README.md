# Spring Many to Many

## Paso 0
  Crea la base de datos chochos si no la tienes previamente creada

  ```psql -U postgres```
```sql
CREATE USER chochos WITH PASSWORD ‘chochos’;
CREATE DATABASE chochos OWNER chochos;
```

## Paso1 Crear los archivos necesarios

1. Crea un nuevo medelo `Department`

``` java
package com.example.demo.model;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Categorias")
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nombre;
    private String descripcion;
    private String tipo;
    private String icono;
    private String imagenNavbar;
    
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "producto_deparments",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "deparments_id")
    )
    private Set<Product> productos = new HashSet<>();

    public Set<Product> getProductos(){
        return this.productos;
    }

    public void setId(String id){
        this.id=id;
    }
    

}
```
2. Crea la iterfaz `categoriaRepo`

```java
package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Department;

public interface categoriaRepo extends JpaRepository <Department,String>{

}

```

Analiza el nuevo modelo `Department`, notarás qye existe una anotación @ManyToMany y otra @JoinTable
Responde ¿Cómo afectará JPA la base de datos? ¿Qué tabla creará? ¿Con qué atributos?

## Paso2 Modificar El modelo principal

1. Agrega la siguiente relación al modelo del producto

```java
@ManyToMany(mappedBy = "productos",fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Department> deparments = new HashSet<>();
```
2. Agrega un método auxiliar para obtener el nuevo set de objetos `departments`

```java
public Set<Department> getDeparments(){
        return this.deparments;
    }
```
3. Investiga qué significa la anotación @JsonIgnore y explica ¿por qué crees que la usamos en producto?

## Paso3 Generar el controlador 
1. Crea el controlador `Categoria`
```java
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

import com.example.demo.model.Department;
import com.example.demo.model.Product;
import com.example.demo.repo.categoriaRepo;
import com.example.demo.repo.productoRepo;

@RestController
public class Categoria {

@Autowired
categoriaRepo repo;
@Autowired
productoRepo repoProducto;
@GetMapping("categoria")
  public List<Department> findAllcategoria() {
    return this.repo.findAll();
  }

@PostMapping("categoria")
  public Department addcategoria(@RequestBody Department categoria) {

    return repo.save(categoria);
  }
  @PostMapping("addProductoCategoria/{productId}/{categoriaId}")
  public Department addcategoria(@PathVariable String productId ,@PathVariable String categoriaId) {
    Optional<Product> p = repoProducto.findById(productId);
    Optional<Department> d = repo.findById(categoriaId);
    if(d.isPresent())
    if(p.isPresent()){
        
        d.get().getProductos().add(p.get());
    }
    return repo.save(d.get());

  }
  @GetMapping("categorias/{productId}")
  public Set<Department>  getDepartmentsByProduct(@PathVariable String productId) {
    Optional<Product> p = repoProducto.findById(productId);
    if(p.isPresent()){
      return  p.get().getDeparments();
    }
    return null;

  }

@GetMapping("categoria/{id}")
public Optional<Department> getById(@PathVariable String id) {
    return repo.findById(id);
}


@PutMapping("categoria/{id}")
public Department putDepartment(@PathVariable String id, @RequestBody Department categoria) {
    categoria.setId(id);
    return repo.save(categoria);
}

@DeleteMapping("categoria/{id}")
public String deleteDeparment(@PathVariable String id) {
    repo.deleteById(id);
    return "{\"success\":true}";
}
}

```
Notarás un endpoint `addProductoCategoria/{productId}/{categoriaId}` analiza el código y responde 
- ¿Cuál crees que es su funcionamiento? 
- ¿Por qué declaramos dos repositorios uno de tipo categoriaRepo y otro d etipo productoRepo?

## Paso4 Hacer pruebas
1. Revisa la base datos y ve las tablas creadas

2. Compara las respuestas de tu archivo entregable con los resultados de estas pruebas, si es necesario modifica tus respuestas.

3. Si no tienes productos creados crea uno
```bash
curl --location 'localhost:8080/producto' \
--header 'Content-Type: application/json' \
--data '{"nombre":"Proteina de fresa", "descripcion":"2.2kg", "lote":"123456", "precioUnitario":1500.5, "fechaCaducidad":"2026-05-04"}'
```

4. Crea una categoría
```bash
curl --location 'localhost:8080/categoria' \
--header 'Content-Type: application/json' \
--data '{
    "nombre":"Suplementos",
    "descripcion":"Suplementos alimenticios, vitaminas y productos naturistas",
    "tipo":"Principal",
    "icono":"<i class='\''bi bi-capsule-pill'\''></i>",
    "imagenNavbar":"https://www.google.com/url?sa=t&source=web&rct=j&url=https%3A%2F%2Fnovanaturaclub.com%2Fsuplementacion-en-deportistas%2F%3Fsrsltid%3DAfmBOor8MBqGUZpYp_4gMRgV4IY5ddSt1CRJ9tXQDjSrBdqVlzf_9zcf&ved=0CBUQjRxqFwoTCMDM2YvFrJEDFQAAAAAdAAAAABAH&opi=89978449"
}'
```
5. Crea la relación entre ambos elementos
> [!CAUTION]
> No olvides agregar los id generados en los comandos anteriores en la siguiente solicitud

```bash
curl --location --request POST 'localhost:8080/addProductoCategoria/{idProducto}/{idCategoria}'
```
6. Revisa la base de datos y verifica que en la tabla `producto_deparments` se tenga un nuevo registro

