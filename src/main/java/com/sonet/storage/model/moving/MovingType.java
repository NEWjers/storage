package com.sonet.storage.model.moving;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "moving_type")
@NoArgsConstructor
@Getter
@Setter
public class MovingType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EMovingType name;

    public MovingType(EMovingType name) {
        this.name = name;
    }
}
