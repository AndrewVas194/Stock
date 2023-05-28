package com.example.StockDiplom.services;

import com.example.StockDiplom.controllers.StuffController;
import com.example.StockDiplom.models.*;
import com.example.StockDiplom.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;
    @Autowired
    private StuffService stuffService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupsService groupsService;

    public void saveTrade(User userId, User user2Id, Groups groupId, Stuff stuffId){
        Trade trade = new Trade();
        trade.setUser1(userId);
        trade.setUser2(user2Id);
        trade.setGroups(groupId);
        trade.setStuff(stuffId);
        tradeRepository.save(trade);
    }

    public void trade(Long user2Id, Long groupId,  Long stuffId) {
        User user1 =  userService.getCurrentUserAuth();
        User user2 =  userService.getUserById(user2Id);
        Groups groups = groupsService.getGroupById(groupId);
        Stuff stuff = stuffService.getStuffById(stuffId);

        Member member1 = memberService.getCurrentMemberByUserIdAndGroupId(user1.getId(),groupId);
        Member member2 = memberService.getCurrentMemberByUserIdAndGroupId(user2Id,groupId);

        if(member1.getGrRole()!=null && member2.getGrRole()!=null) {
            for (Stuff stuffSearch : user2.getStuffList()) {
                if (stuffSearch.equals(stuff)) {
                    stuffService.saveStuffTradeToDB(stuff,user1);
                    saveTrade(user1,user2,groups,stuff);
                    break;
                }
            }
        }
    }
}