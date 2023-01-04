package com.mflix.elasticsyncer;

import java.io.Serializable;

public class SyncObject implements Serializable {

    public String indexName;

    public String sourceJson;

    @Override
    public String toString() {
        return "SyncObject{" +
                "indexName='" + indexName + '\'' +
                ", sourceJson='" + sourceJson + '\'' +
                '}';
    }
}
