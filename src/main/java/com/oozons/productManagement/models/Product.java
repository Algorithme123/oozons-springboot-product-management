package com.oozons.productManagement.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore  // Évite les boucles infinies lors de la conversion JSON
    private Category category;

    // 🔹 Constructeur sans paramètres (obligatoire pour JPA)
    public Product() {
    }

    // 🔹 Constructeur avec tous les paramètres sauf ID (utilisé pour les tests)
    public Product(String code, String libelle, String description, Double price, Category category) {
        this.code = code;
        this.libelle = libelle;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    // 🔹 Constructeur avec ID (utilisé pour les tests et objets récupérés en BD)
    public Product(Long id, String code, String libelle, String description, Double price, Category category) {
        this.id = id;
        this.code = code;
        this.libelle = libelle;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    // 🔹 Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", libelle='" + libelle + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + (category != null ? category.getLibelle() : "null") +
                '}';
    }
}
