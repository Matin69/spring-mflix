package com.mflix.elasticsyncer;

import org.springframework.stereotype.Component;

@Component
public class SyncObjectReceiver {

    private final SyncObjectRepository syncObjectRepository;

    public SyncObjectReceiver(SyncObjectRepository syncObjectRepository) {
        this.syncObjectRepository = syncObjectRepository;
    }

    public void receive(SyncObject syncObject) {
        syncObjectRepository.save(syncObject);
    }
}
