package com.example.StockDiplom.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("/")
    public String home(@RequestParam(name="name", required=false) String name, Model model) {
        model.addAttribute("name", "Stock");
        return "mainPage";
    }
}