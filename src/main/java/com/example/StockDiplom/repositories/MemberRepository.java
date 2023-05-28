package com.example.StockDiplom.repositories;

import com.example.StockDiplom.models.Groups;
import com.example.StockDiplom.models.Member;
import com.example.StockDiplom.models.MemberKey;
import com.example.StockDiplom.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, MemberKey> {
    List<Member> findGroupsByMemberKey(Groups groups);
    List<Member> findByGroups(Groups groups);
    List<Member> findByUser(User user);
    Member findMemberByMemberKey(MemberKey memberKey);
}
