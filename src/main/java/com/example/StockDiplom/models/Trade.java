package com.example.StockDiplom.models;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trade" , schema = "stockcopy")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entityTrade_seq")
    @SequenceGenerator(name="entityTrade_seq", sequenceName="entityTrade_seq", allocationSize = 1)
    private Long id;

    @Column(name = "confirm")
    private boolean confirm;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user1_id")
    private User user1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user2_id")
    private User user2;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stuff_id")
    private Stuff stuff;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Groups groups;
}
