package com.bitcup.whoseturn.storage;

import com.bitcup.whoseturn.model.SelectionHistory;

/**
 * @author bitcup
 */
public class WhoseTurnDao {

    private final WhoseTurnDynamoDbClient dynamoDbClient = new WhoseTurnDynamoDbClient();

    public SelectionHistory getSelectionHistory(String userId, String sessionId) {
        WhoseTurnUserDataItem item = new WhoseTurnUserDataItem();
        item.setCustomerId(userId);
        item = dynamoDbClient.loadItem(item);
        if (item == null) {
            return null;
        }
        SelectionHistory selectionHistory = item.getSelectionHistory();
        selectionHistory.setSession(userId, sessionId);
        return selectionHistory;
    }

    public void saveSelectionHistory(SelectionHistory selectionHistory) {
        WhoseTurnUserDataItem item = new WhoseTurnUserDataItem();
        item.setCustomerId(selectionHistory.getSession().getUser().getUserId());
        item.setSelectionHistory(selectionHistory);
        dynamoDbClient.saveItem(item);
    }

    public void deleteSelectionHistory(SelectionHistory selectionHistory) {
        WhoseTurnUserDataItem item = new WhoseTurnUserDataItem();
        item.setCustomerId(selectionHistory.getSession().getUser().getUserId());
        item.setSelectionHistory(selectionHistory);
        dynamoDbClient.deleteItem(item);
    }
}
