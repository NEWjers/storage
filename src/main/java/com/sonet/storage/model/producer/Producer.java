package com.sonet.storage.model.producer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "producer",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        })
@ToString
@NoArgsConstructor
@Getter
@Setter
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotBlank
    @Size(max = 10)
    private String country;

    @NotBlank
    @Size(max = 200)
    private String description;

    public Producer(String name, String country, String description) {
        this.name = name;
        this.country = country;
        this.description = description;
    }
}
