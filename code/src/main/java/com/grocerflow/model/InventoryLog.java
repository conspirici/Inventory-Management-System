package com.grocerflow.model;

import java.sql.Timestamp;

public class InventoryLog {
    private int logId;
    private int productId;
    private String actionType; 
    private int performedBy; 
    private Timestamp timestamp;

    public InventoryLog() {}

    public InventoryLog(int logId, int productId, String actionType, int performedBy, Timestamp timestamp) {
        this.logId = logId;
        this.productId = productId;
        this.actionType = actionType;
        this.performedBy = performedBy;
        this.timestamp = timestamp;
    }

    public InventoryLog(int productId, String actionType, int performedBy) {
        this.productId = productId;
        this.actionType = actionType;
        this.performedBy = performedBy;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public int getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(int performedBy) {
        this.performedBy = performedBy;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
