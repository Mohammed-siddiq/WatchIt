package com.watchit.watchsellers.dbobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
public class Watch {

    private String watchBrand;

    private String watchName;

    private String watchImage;


}
