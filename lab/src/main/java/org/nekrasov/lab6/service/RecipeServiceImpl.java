package org.nekrasov.lab6.service;

import org.nekrasov.lab6.db.dao.RecipeRepository;
import org.nekrasov.lab6.db.model.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private  RecipeRepository recipeRepository;
    @Override
    public  Iterable<Recipe> listAll(){
        return  recipeRepository.findAll();
    }
    @Transactional
    @Override
    public  void delete(Integer id){
        recipeRepository.deleteById(id);
    }
    @Override
    public Recipe add(String name,String description,String text){
        return recipeRepository.save(new Recipe(name,description,text));
    }
    @Override
    public Recipe findByName(String name){
        return recipeRepository.findByName(name);
    }
}
