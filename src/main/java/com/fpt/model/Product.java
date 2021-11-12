package com.fpt.model;

import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product")
@Data
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "product_name", length = 255, nullable = false, unique = true)
    private String name;

    @Column(name = "image", columnDefinition = "nvarchar(2550)", nullable = false)
    private String image;

    @Column(name = "price", length = 255, nullable = false)
    private double price;

    @Column(name = "title", columnDefinition = "nvarchar(2550)")
    private String title;

    @Column(name = "description", columnDefinition = "nvarchar(2550)")
    private String description;

    @Transient
    private transient int amount = 1;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}