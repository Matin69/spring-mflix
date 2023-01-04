package com.mflix.elasticsyncer;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Repository;

@Repository
public class SyncObjectRepository {

    private final ElasticsearchOperations operations;

    public SyncObjectRepository(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    public void save(SyncObject syncObject) {
        operations.save(
                syncObject.sourceJson,
                IndexCoordinates.of(syncObject.indexName)
        );
    }
}
