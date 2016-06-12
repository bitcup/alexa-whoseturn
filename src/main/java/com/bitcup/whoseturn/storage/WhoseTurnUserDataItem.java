package com.bitcup.whoseturn.storage;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.bitcup.whoseturn.model.SelectionHistory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@DynamoDBTable(tableName = "WhoseTurnUserData")
public class WhoseTurnUserDataItem {

    private String customerId;
    private SelectionHistory selectionHistory;

    @DynamoDBHashKey(attributeName = "CustomerId")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @DynamoDBAttribute(attributeName = "Data")
    @DynamoDBMarshalling(marshallerClass = DataMarshaller.class)
    public SelectionHistory getSelectionHistory() {
        return selectionHistory;
    }

    public void setSelectionHistory(SelectionHistory selectionHistory) {
        this.selectionHistory = selectionHistory;
    }

}
