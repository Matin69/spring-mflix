package com.mflix.app.common;

import java.io.Serializable;

public class SyncObject implements Serializable {

    public String indexName;

    public String sourceJson;

    public SyncObject(String indexName, String sourceJson) {
        this.indexName = indexName;
        this.sourceJson = sourceJson;
    }
}
