package com.sonet.storage.dto.response;

import com.sonet.storage.model.producer.Producer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ItemResponse {

    private Long id;

    private String code;

    private String size;

    private Integer pack;

    private Integer price;

    private String description;

    private Producer producer;
}
