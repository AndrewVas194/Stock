package com.example.StockDiplom.controllers;

import com.example.StockDiplom.models.Enums.roles.GroupRole;
import com.example.StockDiplom.models.Groups;
import com.example.StockDiplom.models.Member;
import com.example.StockDiplom.models.MemberKey;
import com.example.StockDiplom.models.User;
import com.example.StockDiplom.repositories.MemberRepository;
import com.example.StockDiplom.services.GroupsService;
import com.example.StockDiplom.services.MemberService;
import com.example.StockDiplom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.swing.JOptionPane;
import java.util.List;

@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupsService groupsService;

    @GetMapping("/groups/{groupId}/addMember")
    public String showAddMemberForm(@PathVariable Long groupId, Model model) {
        MemberKey memberKey = new MemberKey();
        model.addAttribute("memberKey", memberKey);
        model.addAttribute("groupId", groupId);
        userService.getUsers(model);
        return "groups/member-add";
    }
    //Добавление в группу пользователя
    @PostMapping("/groups/{groupId}/addMember")
    public String addMemberToGroup(@RequestParam(value ="groupId",required = false) Long groupId) {
        Groups groups = groupsService.getGroupById(groupId);
        User user = userService.getCurrentUserAuth();
        MemberKey memberKey = new MemberKey(user.getId(),groupId);
        if(memberService.getCurrentMemberByUserIdAndGroupId(user.getId(),groupId) == null)
        {memberService.saveUserInGroupToDB(memberKey, user, groups, GroupRole.MEMBER);
        return "groups/member-add";}
        else return "errors/error-exist-member";

    }

    @GetMapping("/groups/{groupId}/members")
    public String viewMemberInGroup(@PathVariable Long groupId, Model model){
           // Groups groups = groupsService.getGroupById(groupId);
            List<Member> members = memberService.getMembersGroupById(groupId);
            model.addAttribute("members",members);
            model.addAttribute("groupId", groupId);
            model.addAttribute("title", "Участники");
            return "groups/member-show";
    }

    @GetMapping("/groups/{groupId}/members/delete/{userId}")
    public String showDeleteConfirmation(@PathVariable("userId") Long userId,
                                         @PathVariable("groupId") Long groupId,
                                         Model model) {
        model.addAttribute("groupId", groupId);
        model.addAttribute("userId", userId);
        return "confirm/member-confirm-delete";
    }

    //УДАЛЕНИЕ ПОЛЬЗОВАТЕЛЯ МЕТОД В БД
    @PostMapping("/groups/{groupId}/members/delete/{userId}")
    public String deleteMember(
                               @PathVariable("groupId") Long groupId,
                               @PathVariable("userId") Long userId,
                               Model model) {
        MemberKey memberKey = new MemberKey(userId,groupId);
        Groups group = groupsService.getGroup(groupId);
        User user = userService.getUser(userId);
        memberService.deleteMemberFromGroup(memberKey,groupId);
        groupsService.saveGroup(group);
        model.addAttribute("title", "Пользователь выгнан из группы");
        model.addAttribute("message", "Пользователь выгнан из группы");
        return "groups/delete-member-success";
    }
}