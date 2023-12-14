/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.nekrasov.kafka;

import javax.transaction.Transactional;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class KafkaConsumerListeners {

    @Autowired
    RecipeRepository recipeRepository;

    @Transactional
    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.groupid}")
    public void debeziumListener1(ConsumerRecord<org.apache.avro.generic.GenericData.Record, org.apache.avro.generic.GenericData.Record> record) {

        if (record.value() == null) {
            return;
        }

        String op = ((org.apache.avro.util.Utf8) record.value().get("op")).toString();

        if (op.equals("d")) {
            Integer idToDelete = (Integer) record.key().get("recipe_id");
            try {
                recipeRepository.deleteById(idToDelete);
            } catch (org.springframework.dao.EmptyResultDataAccessException e) {
                //Команда удаления приходит из удаленного лога репликации
                //в том числе и в ответ на ответ на удаление в локальном. 
                //В этом случае удалить повторно запись уже нельзя
            }
            return;
        }

        org.apache.avro.generic.GenericData.Record value = (org.apache.avro.generic.GenericData.Record) record.value().get("after");
        Integer id = (Integer) value.get("recipe_id");
        String name = ((org.apache.avro.util.Utf8) value.get("recipe_name")).toString();
        String description = ((org.apache.avro.util.Utf8) value.get("recipe_description")).toString();
        String text = ((org.apache.avro.util.Utf8) value.get("recipe_text")).toString();

        Recipe recipe = recipeRepository.findById(id).orElse(null);

        if (recipe == null) {
            recipeRepository.save(new Recipe(id, name, description, text));
        } else {
            recipe.setName(name);
            recipe.setDescription(description);
            recipe.setText(text);
            recipeRepository.save(recipe);
        }
    }

}
