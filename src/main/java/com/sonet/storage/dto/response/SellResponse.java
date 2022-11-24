package com.sonet.storage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class SellResponse {

    private Long id;

    private String date;

    private List<MovingRecordResponse> items;
}
