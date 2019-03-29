package com.ejtrio.springdatarest.infrastructure.models.projections;

import com.ejtrio.springdatarest.infrastructure.models.Item;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "itemDetail", types = { Item.class })
public interface ItemDetail {

    String getItemName();

    String getDescription();

    Double getPrice();
}
