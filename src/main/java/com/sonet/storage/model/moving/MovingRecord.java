package com.sonet.storage.model.moving;

import com.sonet.storage.model.item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "moving_record")
@NoArgsConstructor
@ToString
@Getter
@Setter
public class MovingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long count;

    private String date;

    @OneToOne
    @JoinTable(  name = "moving_record_type",
            joinColumns = @JoinColumn(name = "record_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id"))
    private MovingType type;

    @OneToOne
    private Item item;
}
