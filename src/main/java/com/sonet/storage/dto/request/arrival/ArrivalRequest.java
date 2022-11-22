package com.sonet.storage.dto.request.arrival;

import com.sonet.storage.model.item.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArrivalRequest {

    private String itemCount;

    private Item item;
}
