package com.sonet.storage.dto.response;

import com.sonet.storage.model.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RecordResponse {

    private Long id;

    private Long count;

    private Item item;
}
