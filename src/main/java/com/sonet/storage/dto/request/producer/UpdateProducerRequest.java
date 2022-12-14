package com.sonet.storage.dto.request.producer;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateProducerRequest {

    @NotBlank
    private Long id;

    @Size(min = 3, max = 20)
    private String name;

    @Size(min = 3, max = 30)
    private String country;

    @Size(max = 20)
    private String description;
}
