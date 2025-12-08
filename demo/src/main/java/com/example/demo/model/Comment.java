package com.example.demo.model;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="comentarios")
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String comentario_corto;
    private Short estrellas;
    private String comentario_largo;
    private String imagen1;
    private String imagen2;
    
    @JsonBackReference
    @ManyToOne(fetch=FetchType.LAZY)
    private Product producto;

    public Comment(String username, String comentario_corto, Short estrellas, String comentario_largo, String imagen1,
            String imagen2, Product producto) {
        this.username = username;
        this.comentario_corto = comentario_corto;
        this.estrellas = estrellas;
        this.comentario_largo = comentario_largo;
        this.imagen1 = imagen1;
        this.imagen2 = imagen2;
        this.producto = producto;
    }

    

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setComentario_corto(String comentario_corto) {
        this.comentario_corto = comentario_corto;
    }

    public void setEstrellas(Short estrellas) {
        this.estrellas = estrellas;
    }

    public void setComentario_largo(String comentario_largo) {
        this.comentario_largo = comentario_largo;
    }

    public void setImagen1(String imagen1) {
        this.imagen1 = imagen1;
    }

    public void setImagen2(String imagen2) {
        this.imagen2 = imagen2;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getComentario_corto() {
        return comentario_corto;
    }

    public Short getEstrellas() {
        return estrellas;
    }

    public String getComentario_largo() {
        return comentario_largo;
    }

    public String getImagen1() {
        return imagen1;
    }

    public String getImagen2() {
        return imagen2;
    }

    public Product getProducto() {
        return producto;
    }



    public void setProducto(Product product) {
        this.producto = product;
    }

}