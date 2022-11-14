package com.sonet.storage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProducerResponse {

    private Long id;

    private String name;

    private String country;

    private String description;
}
