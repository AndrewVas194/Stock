package com.example.StockDiplom.services;

import com.example.StockDiplom.models.Enums.Category;
import com.example.StockDiplom.models.Enums.Conditional;
import com.example.StockDiplom.models.Enums.Gender;
import com.example.StockDiplom.models.Enums.Status;
import com.example.StockDiplom.models.Stuff;
import com.example.StockDiplom.models.User;
import com.example.StockDiplom.repositories.StuffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class StuffService {
    @Autowired
    private StuffRepository stuffRepository;

    public Model getStuff(Model model){
        Iterable<Stuff> stuffs = stuffRepository.findAll();
        return model.addAttribute("stuffs",stuffs);
    }
    public void saveStuffToDB(String name, String description, Category category,
                              Gender gender, Short age, Status status,
                              Conditional conditional, User user){
        Stuff stuff = new Stuff();
        stuff.setName(name);
        stuff.setDescription(description);
        stuff.setCategory(category);
        stuff.setGender(gender);
        stuff.setAge(age);
        stuff.setStatus(status);
        stuff.setConditional(conditional);
        stuff.setUser(user);
        stuffRepository.save(stuff);
    }

    public void saveStuffTradeToDB(Stuff stuff, User user){
        stuff.setUser(user);
        stuffRepository.save(stuff);
    }

    public Stuff getStuffById(Long id){
        return stuffRepository.findStuffById(id);
    }

    public Iterable<Stuff> getAllStuff(){
        return stuffRepository.findAll();
    }
}
