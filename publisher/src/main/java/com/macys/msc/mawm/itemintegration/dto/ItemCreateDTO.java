package com.macys.msc.mawm.itemintegration.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class ItemCreateDTO {
    String color;
    String description;
    String hazardousMaterialId;
    Integer hazmat;
    String merchandizingDepartmentId;
    Double monetaryValue;
    Double monetaryValueCurrencyCode;
    String primaryBarCode;
    String sizeCode;
    String style;
    Double unitPrice;
    String itemId;
    String imageUrl;
    ExtendedDTO extended;
}

