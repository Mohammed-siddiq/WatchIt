package com.watchit.watchsellers.dbobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "watches")
public class Watch {

    private String brand;

    private String family;

    private String name;

    private String limited;

    private String caseMaterial;

    private String caseBack;

    private String caseShape;

    private String caseDiameter;

    private String dialIndexes;

    private String dialColor;

    private String movementTime;

    private String description;

    private String imageLink;


}
