package com.example.StockDiplom.services;

import com.example.StockDiplom.models.Enums.roles.GroupRole;
import com.example.StockDiplom.models.Groups;
import com.example.StockDiplom.models.Member;
import com.example.StockDiplom.models.MemberKey;
import com.example.StockDiplom.models.User;
import com.example.StockDiplom.repositories.GroupsRepository;
import com.example.StockDiplom.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private GroupsRepository groupsRepository;

    public void saveUserInGroupToDB(MemberKey id, User user, Groups groups, GroupRole groupRole){
        Member member = new Member(id, user, groups, groupRole);
        memberRepository.save(member);
        groupsRepository.save(groups);
    }

    public List<Member> getMembersGroupById(Long id) {
        return groupsRepository.findById(id).orElse(null).getMemberGroup();
    }

    public Member getCurrentMemberByUserIdAndGroupId(Long userId,Long groupId){
        MemberKey memberKey = new MemberKey(userId,groupId);
        Member member = memberRepository.findMemberByMemberKey(memberKey);
        return member;
    }

    public void saveMember(Member member) {
        memberRepository.save(member);
    }

    public void deleteMemberFromGroup(MemberKey memberId, Long groupId) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            memberRepository.deleteById(memberId);
            member.getUser().getMemberUser().remove(member);
            member.getGroups().getMemberGroup().remove(member);
        }
    }
}