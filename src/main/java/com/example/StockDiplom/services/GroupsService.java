package com.example.StockDiplom.services;

import com.example.StockDiplom.models.Enums.roles.GroupRole;
import com.example.StockDiplom.models.Groups;
import com.example.StockDiplom.models.Member;
import com.example.StockDiplom.models.MemberKey;
import com.example.StockDiplom.models.User;
import com.example.StockDiplom.repositories.GroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class GroupsService {
    @Autowired
    private GroupsRepository groupsRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private UserService userService;

    public Model getGroups(Model model){
        Iterable<Groups> groups = getAllGroups();
        return model.addAttribute("groups",groups);
    }

    public void saveGroupsToDB(String name){
        User user = userService.getCurrentUserAuth();
        Groups groups = new Groups();
        groups.setName(name);
        saveGroup(groups);
        MemberKey memberKey = new MemberKey(user.getId(),groups.getId());
        memberService.saveUserInGroupToDB(memberKey,user,groups, GroupRole.HOST);
    }

    // Метод для сохранения группы в базу данных
    public Groups saveGroup(Groups groups) {
        return groupsRepository.save(groups);
    }
    //Получение группы по id
    public Groups getGroupById(Long id){
        return groupsRepository.findById(id).orElse(null);
    }

    // Метод для получения всех групп
    public Iterable<Groups> getAllGroups() {
        return groupsRepository.findAll();
    }
    //получение группы по имени
    public Groups getGroupByName(String name){
        return groupsRepository.findGroupsByName(name);
    }

    public void deleteGroupById(Long id){
        groupsRepository.deleteById(id);
    }

    public Groups getGroup(Long groupId) {
        return groupsRepository.findById(groupId)
                .orElseThrow();
    }
}