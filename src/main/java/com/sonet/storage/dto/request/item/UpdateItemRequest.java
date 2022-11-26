package com.sonet.storage.dto.request.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateItemRequest {

    private Long id;

    private String code;

    private String size;

    private Integer pack;

    private Double price;

    private String description;

    private Long producerId;
}
