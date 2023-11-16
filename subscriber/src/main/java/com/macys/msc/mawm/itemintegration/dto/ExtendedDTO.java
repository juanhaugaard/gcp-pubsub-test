package com.macys.msc.mawm.itemintegration.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class ExtendedDTO {
    String subclass;
    String businessUnit;
    String vendorId;
    String prop65;
    String markDownStatus;
}
