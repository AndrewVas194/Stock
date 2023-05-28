package com.example.StockDiplom.controllers;

import com.example.StockDiplom.models.Enums.roles.GroupRole;
import com.example.StockDiplom.models.Enums.roles.Role;
import com.example.StockDiplom.models.Groups;
import com.example.StockDiplom.models.Member;
import com.example.StockDiplom.models.User;
import com.example.StockDiplom.repositories.GroupsRepository;
import com.example.StockDiplom.services.GroupsService;
import com.example.StockDiplom.services.MemberService;
import com.example.StockDiplom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/groups")
public class GroupsController {

    @Autowired
    private GroupsRepository groupsRepository;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private UserService userService;

    @Autowired
    private MemberService memberService;

    @GetMapping("/{id}")
    public String groupsById(@PathVariable("id") Long id,
                           Model model){
        Groups groups = groupsService.getGroupById(id);
        if(groups == null){
            return "errors/error-groups";
        }
        model.addAttribute("groups",groups);
        return "groups/groups-id";
    }

    @GetMapping("/list")
    public String groupsList(Model model) {
        Iterable<Groups> groups = groupsRepository.findAll();
        model.addAttribute("groups", groups);
        model.addAttribute("title", "Группы");
        return "groups/groups-list";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/add")
    public String groupsAdd(Model model) {
        model.addAttribute("title", "Добавление группы");
        return "groups/groups-add";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/add")
    public String groupsSave(
            @RequestParam("groupsName") String name
    ) {
        groupsService.saveGroupsToDB(name);
        return "groups/groups-add";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteConfirmation(@PathVariable("id") Long id, Model model) {
        User user = userService.getCurrentUserAuth();
        if(
                user.getRoles().contains(Role.ADMIN) ||
                        memberService.getCurrentMemberByUserIdAndGroupId(user.getId(),id).getGrRole() == GroupRole.HOST
        ) {
            model.addAttribute("id", id);
            return "confirm/groups-confirm-delete";
        }
        else {
            return "errors/error-groups";
        }
    }

    //УДАЛЕНИЕ ГРУППЫ
    @PostMapping("/delete/{id}")
    public String deleteGroups(@PathVariable("id") Long id, Model model) {
        groupsService.deleteGroupById(id);
        model.addAttribute("title", "Успешное удаление группы");
        model.addAttribute("message", "Группа успешно удалена");
        return "groups/delete-groups-success";
    }
}