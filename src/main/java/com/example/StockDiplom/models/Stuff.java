package com.example.StockDiplom.models;

import com.example.StockDiplom.models.Enums.Category;
import com.example.StockDiplom.models.Enums.Conditional;
import com.example.StockDiplom.models.Enums.Gender;
import com.example.StockDiplom.models.Enums.Status;
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
@Table(name = "stuff" , schema = "stockcopy")
public class Stuff {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entityStuff_seq")
    @SequenceGenerator(name="entityStuff_seq", sequenceName="entityStuff_seq", allocationSize = 1)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "category")
    private Category category;
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "age")
    private Short age;
    @Column(name = "status")
    private Status status;
    @Column(name = "conditional")
    private Conditional conditional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "stuff", cascade = CascadeType.ALL)
    private List<Trade> trade;

    public Stuff getStuff (){
        return this;
    }
}
