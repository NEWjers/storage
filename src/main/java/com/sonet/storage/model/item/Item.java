package com.sonet.storage.model.item;

import com.sonet.storage.model.producer.Producer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "item",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "code")
        })
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String size;

    private Integer pack;

    private Integer price;

    private String description;

    @ManyToOne
    private Producer producer;

    public Item(String code, String size, Integer pack, Integer price, String description, Producer producer) {
        this.code = code;
        this.size = size;
        this.pack = pack;
        this.price = price;
        this.description = description;
        this.producer = producer;
    }
}
