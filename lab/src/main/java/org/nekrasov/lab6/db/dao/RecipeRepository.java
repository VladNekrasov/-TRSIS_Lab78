package org.nekrasov.lab6.db.dao;

import org.nekrasov.lab6.db.model.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe,Integer> {
    public  Recipe findByName(String name);
}