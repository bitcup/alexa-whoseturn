package com.bitcup.whoseturn.storage;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

/**
 * @author bitcup
 */
public class WhoseTurnDynamoDbClient {
    private final AmazonDynamoDBClient dynamoDBClient = AmazonDynamoDbClientHolder.getInstance();

    /**
     * Loads an item from DynamoDB by primary Hash Key. Callers of this method should pass in an
     * object which represents an item in the DynamoDB table item with the primary key populated.
     */
    public WhoseTurnUserDataItem loadItem(final WhoseTurnUserDataItem tableItem) {
        DynamoDBMapper mapper = createDynamoDBMapper();
        return mapper.load(tableItem);
    }

    /**
     * Stores an item to DynamoDB.
     */
    public void saveItem(final WhoseTurnUserDataItem tableItem) {
        DynamoDBMapper mapper = createDynamoDBMapper();
        mapper.save(tableItem);
    }

    public void deleteItem(final WhoseTurnUserDataItem tableItem) {
        DynamoDBMapper mapper = createDynamoDBMapper();
        mapper.delete(tableItem);
    }

    /**
     * Creates a {@link DynamoDBMapper} using the default configurations.
     */
    private DynamoDBMapper createDynamoDBMapper() {
        return new DynamoDBMapper(dynamoDBClient);
    }
}
