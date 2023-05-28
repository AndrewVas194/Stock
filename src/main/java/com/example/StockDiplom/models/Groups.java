package com.example.StockDiplom.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`groups`", schema = "stockcopy")
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entityGroups_seq")
    @SequenceGenerator(name="entityGroups_seq", sequenceName="entityGroups_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;


    @OneToMany(mappedBy = "groups", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Member> memberGroup;

}