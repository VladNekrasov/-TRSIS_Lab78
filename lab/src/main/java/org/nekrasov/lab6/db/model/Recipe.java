package org.nekrasov.lab6.db.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "RECIPE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe implements Serializable {
    private static final long serialVersionUID = 1L;
    public Recipe(String name, String description, String text) {
        this.name = name;
        this.description = description;
        this.text = text;
    }

    @Id
    @Column(name = "RECIPE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "RECIPE_NAME",unique = true)
    private String name;

    @Column(name = "RECIPE_DESCRIPTION")
    private String description;

    @Column(name = "RECIPE_TEXT",length = 1000)
    private String text;
}
