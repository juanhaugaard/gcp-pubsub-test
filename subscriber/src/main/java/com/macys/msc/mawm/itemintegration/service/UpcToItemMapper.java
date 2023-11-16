package com.macys.msc.mawm.itemintegration.service;

import com.macys.msc.mawm.itemintegration.dto.ItemCreateDTO;
import com.macys.pag.client.core.generated.types.Upc;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UpcToItemMapper {

    @Mapping(target = "color", source = "upc.variation.legacyAttributes.colorCode")
    @Mapping(target = "description", source = "upc.upcName")
    @Mapping(target = "merchandizingDepartmentId", source = "upc.classification.department.departmentId")
    @Mapping(target = "primaryBarCode", source = "upc.upcNumber")
    @Mapping(target = "sizeCode", source = "upc.variation.legacyAttributes.sizeCode")
    @Mapping(target = "style", source = "upc.classification.markStyle")
    @Mapping(target = "itemId", source = "upc.upcNumber")
    @Mapping(target = "imageUrl", source = "upc.variation.colorway.primaryImage.imageName")
    @Mapping(target = "extended.subclass", source = "upc.classification.subClassId")
    @Mapping(target = "extended.businessUnit", source = "upc.divisionId")
    @Mapping(target = "extended.vendorId", source = "upc.classification.vendor.vendorId")
    ItemCreateDTO pagUpcToItemCreateDTO(Upc upc);

}
