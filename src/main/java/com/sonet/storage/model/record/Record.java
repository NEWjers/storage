package com.sonet.storage.model.record;

import com.sonet.storage.model.item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "record",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "item_id")
        })
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long count;

    @OneToOne
    private Item item;

    public Record(Long count, Item item) {
        this.count = count;
        this.item = item;
    }
}
