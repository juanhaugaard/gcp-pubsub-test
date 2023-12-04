package com.macys.msc.mawm.itemintegration.service;

import com.macys.msc.mawm.itemintegration.dto.ItemCreateDTO;
import com.macys.pag.client.core.generated.types.Upc;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class UpcToItemMapper {
    private static final String IMG_URL_FORMAT = "https://slimages.macys.com/is/image/MCY/products/4/optimized/%s.tif";

    @Mapping(target = "color", source = "upc.variation.legacyAttributes.colorCode")
    @Mapping(target = "description", source = "upc.upcName")
    @Mapping(target = "merchandizingDepartmentId", source = "upc.classification.department.departmentId")
    @Mapping(target = "primaryBarCode", source = "upc.upcNumber")
    @Mapping(target = "sizeCode", source = "upc.variation.legacyAttributes.sizeCode")
    @Mapping(target = "style", source = "upc.classification.markStyle")
    @Mapping(target = "itemId", source = "upc.upcNumber")
    @Mapping(target = "imageUrl", source = "upc.variation.colorway.primaryImage.imageName")
    @Mapping(target = "extended.classId", source = "upc.classification.classId")
    @Mapping(target = "extended.subClass", source = "upc.classification.subClassId")
    @Mapping(target = "extended.vendorID", source = "upc.classification.vendor.vendorId")
    @Mapping(target = "extended.division", source = "upc.divisionId")
    @Mapping(target = "unitPrice", expression = "java(upc.getPrice()!=null && upc.getPrice().size()>0 && upc.getPrice().get(0).getRetailPrice()!= null? new Double(upc.getPrice().get(0).getRetailPrice()):null)")
    @Mapping(target = "monetaryValue", expression = "java(upc.getPrice()!=null && upc.getPrice().size()>0 && upc.getPrice().get(0).getOwnPrice()!=null? new Double(upc.getPrice().get(0).getOwnPrice()):null)")

    public abstract ItemCreateDTO pagUpcToItemCreateDTO(Upc upc);

    @AfterMapping
    protected void addLeadingZeroesToMerchandizingDepartmentId(@MappingTarget ItemCreateDTO itemCreateDTO) {
        if(itemCreateDTO.getMerchandizingDepartmentId() != null){
            itemCreateDTO.setMerchandizingDepartmentId(String.format("%04d", Integer.parseInt(itemCreateDTO.getMerchandizingDepartmentId())));
        }
    }
    @AfterMapping
    protected void prependAndConvertImgURL(@MappingTarget ItemCreateDTO itemCreateDTO){
        String imgFileName = itemCreateDTO.getImageUrl();
        if(imgFileName!= null){
            imgFileName = imgFileName.replace(".","_");
            String imgURL = String.format(IMG_URL_FORMAT,imgFileName);
            itemCreateDTO.setImageUrl(imgURL);
        }
    }
}
