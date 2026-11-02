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
import lombok.Setter;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="imagenes")
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter 
    private String id;
    private String url;
    private Short prioridad;
    private String tamanio;
    private boolean responsiva;
    
    @JsonBackReference
    @ManyToOne(fetch=FetchType.LAZY)
    @Setter 
    private Producto producto;

}