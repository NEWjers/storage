package com.sonet.storage.model.arrival;

import com.sonet.storage.model.moving.MovingRecord;
import com.sonet.storage.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "arrival")
@NoArgsConstructor
@Getter
@Setter
public class Arrival {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;

    @ManyToOne
    private User user;

    @OneToMany
    private List<MovingRecord> items;

    public Arrival(String date, List<MovingRecord> items) {
        this.date = date;
        this.items = items;
    }
}
