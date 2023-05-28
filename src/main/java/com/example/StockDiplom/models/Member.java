package com.example.StockDiplom.models;

import com.example.StockDiplom.models.Enums.roles.GroupRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member", schema = "stockcopy")
public class Member {
    @EmbeddedId
    private MemberKey memberKey;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("groupsId")
    @JoinColumn(name = "groups_id")
    private Groups groups;

    private GroupRole grRole;



}
