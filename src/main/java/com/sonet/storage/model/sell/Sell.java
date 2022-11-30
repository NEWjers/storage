package com.sonet.storage.model.sell;

import com.sonet.storage.model.moving.MovingRecord;
import com.sonet.storage.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sell")
@NoArgsConstructor
@Getter
@Setter
public class Sell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;

    @ManyToOne
    private User user;

    @OneToMany
    private List<MovingRecord> items;

    public Sell(String date, List<MovingRecord> items) {
        this.date = date;
        this.items = items;
    }
}
