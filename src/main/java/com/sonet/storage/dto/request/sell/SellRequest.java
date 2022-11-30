package com.sonet.storage.dto.request.sell;

import com.sonet.storage.model.item.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellRequest {

    private String itemCount;

    private String username;

    private Item item;
}
