package org.nekrasov.lab6.service;
import org.nekrasov.lab6.db.model.Recipe;

public interface RecipeService {
    Iterable<Recipe> listAll();
    void delete(Integer id);
    Recipe add(String name,String description, String text);

    Recipe findByName(String name);
}
