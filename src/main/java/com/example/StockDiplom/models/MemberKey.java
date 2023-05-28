package com.example.StockDiplom.models;


import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
public class MemberKey implements Serializable {
    @Column(name = "user_id")
    Long userId;

    @Column(name = "groups_id")
    Long groupsId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberKey memberKey)) return false;
        return Objects.equals(userId, memberKey.userId) && Objects.equals(groupsId, memberKey.groupsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, groupsId);
    }

    public MemberKey() {
    }

    public MemberKey(Long userId, Long groupsId) {
        this.userId = userId;
        this.groupsId = groupsId;
    }
}
