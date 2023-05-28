package com.example.StockDiplom.repositories;

import com.example.StockDiplom.models.Enums.Gender;
import com.example.StockDiplom.models.Stuff;
import com.example.StockDiplom.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User findByUsername (String name);
  //  User findById(long id);
    List<User> findByName(String name);
    List<User> findByEmail(String email);
    List<User> findByActive(boolean active);
}
