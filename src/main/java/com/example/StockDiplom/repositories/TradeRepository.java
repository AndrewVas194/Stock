package com.example.StockDiplom.repositories;

import com.example.StockDiplom.models.Groups;
import com.example.StockDiplom.models.Stuff;
import com.example.StockDiplom.models.Trade;
import com.example.StockDiplom.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends CrudRepository<Trade,Long> {
    List<Trade> findTradeByStuff(Stuff stuff);
    List<Trade> findTradeByGroups(Groups groups);
    List<Trade> findTradeByUser1 (User user);
    List<Trade> findTradeByUser2 (User user);
    List<Trade> findTradeById (Long id);
}
