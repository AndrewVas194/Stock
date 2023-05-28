package com.example.StockDiplom.controllers;

import com.example.StockDiplom.models.Groups;
import com.example.StockDiplom.models.Member;
import com.example.StockDiplom.models.Stuff;
import com.example.StockDiplom.models.User;
import com.example.StockDiplom.services.GroupsService;
import com.example.StockDiplom.services.MemberService;
import com.example.StockDiplom.services.StuffService;
import com.example.StockDiplom.services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private MemberService memberService;


   /* @GetMapping("/trade/{user2Id}")
    public String showTradeForm(){
        return "/";
    }

    @PostMapping("/trade/{user2Id}/{stuffId}")
    public String performTrade(
                                     @PathVariable Long user2Id,
                                     @PathVariable Long stuffId) {
        tradeService.trade(user2Id,  stuffId);
        return "trade/trade-groups";
    }*/

    @GetMapping("/groups/{groupId}/{user2Id}")
    public String showTradeFormGroups(@PathVariable Long groupId,@PathVariable Long user2Id, Model model) {

        Member member2 = memberService.getCurrentMemberByUserIdAndGroupId(user2Id,groupId);
        List<Stuff> stuffList = member2.getUser().getStuffList();

        model.addAttribute("user2Id",user2Id);
        model.addAttribute("groupId", groupId);
        model.addAttribute("stuffList", stuffList);
        return "trade/trade-groups-show";
    }

    @PostMapping("/groups/{groupId}/{user2Id}/{stuffId}")
    public String performTradeGroups(@PathVariable Long groupId,
                                     @PathVariable Long user2Id,
                                     @PathVariable Long stuffId) {
        tradeService.trade(user2Id, groupId, stuffId);
        return "groups/member-show";
    }


}