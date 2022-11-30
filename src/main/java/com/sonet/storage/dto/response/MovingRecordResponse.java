package com.sonet.storage.dto.response;

import com.sonet.storage.model.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MovingRecordResponse {

    private Long count;

    private String date;

    private String type;

    private String username;

    private Item item;
}
