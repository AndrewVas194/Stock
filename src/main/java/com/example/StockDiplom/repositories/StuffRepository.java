package com.example.StockDiplom.repositories;

import com.example.StockDiplom.models.Enums.Gender;
import com.example.StockDiplom.models.Stuff;
import com.example.StockDiplom.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StuffRepository extends CrudRepository <Stuff,Long> {
    List<Stuff> findByName(String name);
    List<Stuff> findByCategory(String category);
    List<Stuff> findByAge(Short age);
    List<Stuff> findByGender(Gender gender);
    List<Stuff> findByConditional(String conditional);
    List<Stuff> findByUserId(User user);
    Stuff findStuffById(Long id);
    //List<Stuff> findByUserId(Long id);
}
