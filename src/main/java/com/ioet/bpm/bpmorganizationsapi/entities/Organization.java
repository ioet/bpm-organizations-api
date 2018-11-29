package com.ioet.bpm.bpmorganizationsapi.entities;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;


import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@Data
@DynamoDBTable(tableName = "Organization")
public class Organization {

    @DynamoDBHashKey
    @Generated
    private String uid;

    @NotBlank
    @DynamoDBAttribute
    private String organizationName;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
