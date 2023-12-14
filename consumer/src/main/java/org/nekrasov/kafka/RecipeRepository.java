package org.nekrasov.kafka;

import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe,Integer> {
    public  Recipe findByName(String name);
}