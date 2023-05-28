package com.example.StockDiplom.controllers;

import com.example.StockDiplom.models.Enums.Category;
import com.example.StockDiplom.models.Enums.Conditional;
import com.example.StockDiplom.models.Enums.Gender;
import com.example.StockDiplom.models.Enums.Status;
import com.example.StockDiplom.models.Stuff;
import com.example.StockDiplom.models.User;
import com.example.StockDiplom.repositories.StuffRepository;
import com.example.StockDiplom.repositories.UserRepository;
import com.example.StockDiplom.services.StuffService;
import com.example.StockDiplom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/stuff")
public class StuffController {
    @Autowired
    private StuffService stuffService;

    @Autowired
    private StuffRepository stuffRepository;

    @Autowired
    private UserService userService;


    @GetMapping("/list")
    public String stuffList (Model model){
        Iterable<Stuff> stuffs = stuffRepository.findAll();
        model.addAttribute("stuffs",stuffs);
        return "stuff/stuff-list";
    }

    @GetMapping ("/add")
    public String stuffAdd (Model model) {
        model.addAttribute("title","Добавление вещей");
        userService.getUsers(model);
        return "stuff/stuff-add";
    }

    @PostMapping("/add")
    public String stuffSave(
            @RequestParam("stuffName") String name,
            @RequestParam("stuffDescription") String description,
            @RequestParam("stuffCategory") Category category,
            @RequestParam("stuffGender") Gender gender,
            @RequestParam("stuffAge") Short age,
            @RequestParam("stuffConditional") Conditional conditional
    ) {
        stuffService.saveStuffToDB( name, description, category, gender, age, Status.Свободно, conditional, userService.getCurrentUserAuth());
        return "stuff/stuff-add";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteConfirmation(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        return "confirm/stuff-confirm-delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteStuff(@PathVariable("id") Long id, Model model) {
        stuffRepository.deleteById(id);
        model.addAttribute("title","Успешное удаление вещи");
        model.addAttribute("message", "Объект успешно удален");
        return "stuff/delete-stuff-success";
    }

    @GetMapping("/gender")
    public String filter(@RequestParam Gender filter, Model model){
        List<Stuff> stuffs = stuffRepository.findByGender(filter);
        model.addAttribute("stuffs", stuffs);
        model.addAttribute("title","Пол");
        return "stuff/stuff-list";
    }
}
