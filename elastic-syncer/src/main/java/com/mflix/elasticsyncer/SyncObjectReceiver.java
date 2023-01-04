package com.mflix.elasticsyncer;

import org.springframework.stereotype.Component;

@Component
public class SyncObjectReceiver {

    public void receive(SyncObject syncObject) {
        System.out.println(syncObject);
    }
}
