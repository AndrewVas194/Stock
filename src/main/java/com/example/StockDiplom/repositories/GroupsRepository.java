package com.example.StockDiplom.repositories;

import com.example.StockDiplom.models.Groups;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsRepository extends CrudRepository<Groups, Long> {
    Groups findGroupsByName(String name);
}
