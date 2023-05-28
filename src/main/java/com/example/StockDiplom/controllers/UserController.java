package com.example.StockDiplom.controllers;

import com.example.StockDiplom.models.Stuff;
import com.example.StockDiplom.models.User;
import com.example.StockDiplom.services.MemberService;
import com.example.StockDiplom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MemberService memberService;

    //ПОЛУЧЕНИЕ ОТ ПОЛЬЗОВАТЕЛЯ ВЕЩЕЙ
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{userId}")
    public String getUserStuff(@PathVariable(value ="userId") Long userId, Model model) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return "errors/error-user-stuff";
        }
        List<Stuff> stuffList = user.getStuffList();
        model.addAttribute("user",user);
        model.addAttribute("userId",userId);
        model.addAttribute("stuffList", stuffList);
        return "user/user-view-for-admin";
    }

    //ВЫВОД ВСЕХ ПОЛЬЗОВАТЕЛЕЙ
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/list")
    public String userList (Model model){
        Iterable<User> users = userService.findAllUsers();
        model.addAttribute("users",users);
        return "user/user-list";
    }

    //ДОБАВЛЕНИЕ ПОЛЬЗОВАТЕЛЯ ОТПРАВКА В БД
    /*@PostMapping("/add")
    public String userSave(
            @RequestParam("userLogin") String login,
            @RequestParam("userPassword") String password,
            @RequestParam("userName") String name,
            @RequestParam("userSurName") String surname,
            @RequestParam("userPhone") String phone,
            @RequestParam("userEmail") String email,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {
        userService.saveUserToDB(user, login, password, name,surname,phone,email, form);
        return "redirect:/user-add";
    }*/

    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam String name,
            @RequestParam Boolean active,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {
        userService.saveUserRegister(user,username,name,active,form);
        return "redirect:/user";
    }

    //УДАЛЕНИЕ ПОЛЬЗОВАТЕЛЯ
    @GetMapping("/delete/{id}")
    public String showDeleteConfirmation(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        return "confirm/user-confirm-delete";
    }

    //УДАЛЕНИЕ ПОЛЬЗОВАТЕЛЯ МЕТОД В БД
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        userService.deleteUserById(id);
        model.addAttribute("title", "Успешное удаление пользователя");
        model.addAttribute("message", "Пользователь успешно удален");
        return "user/delete-user-success";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("account")
    public String getAccount(Model model){
        User user = userService.getCurrentUserAuth();
        if(user.getUsername() != null && user.getUsername() != ""){
        List<Stuff> stuffList = user.getStuffList();
        model.addAttribute("user",user);
        model.addAttribute("stuffList",stuffList);
        model.addAttribute("title","Личный кабинет");
            return "user/account";
        }
        else {
            return "errors/error-enter";
        }
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("account")
    public String updateAccount(@RequestParam String email,
                                @RequestParam String password,
                                Model model){
        User user = userService.getCurrentUserAuth();
        userService.updateAccount(user,email,password);
        model.addAttribute("email",email);
        model.addAttribute("password",password);
        return "redirect:/user/account";
    }

    //Отключение пользователя
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{user}/turnOff")
    public String userTurnOff(@RequestParam("userId") User user, Model model)
    {
        model.addAttribute("user",user);
        userService.userTurnOffActive(user);
        return "redirect:/user";
    }

    //Поисковики
    @GetMapping("/name")
    public String name(@RequestParam String name, Model model){
        List<User> users = userService.getUsersByName(name);
        if (name == null) {
            return "errors/error-user-stuff";
        }
        model.addAttribute("users",users);
        model.addAttribute("title","Имя");
        return "user/user-list";
    }
    @GetMapping("/email")
    public String email(@RequestParam String email, Model model){
        List<User> users = userService.getUsersByEmail(email);
        if (email == null) {
            return "errors/error-user-stuff";
        }
        model.addAttribute("users",users);
        model.addAttribute("title","Почта");
        return "user/user-list";
    }
    @GetMapping("/active")
    public String active(@RequestParam boolean active, Model model){
        List<User> users = userService.getUsersByActive(active);
        model.addAttribute("users",users);
        model.addAttribute("title","Активность");
        return "user/user-list";
    }
}
