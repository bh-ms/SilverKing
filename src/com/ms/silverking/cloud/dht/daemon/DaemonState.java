package com.ms.silverking.cloud.dht.daemon;

public enum DaemonState {
    INITIAL,
    INITIAL_MAP_WAIT,
    RECOVERY,
    QUORUM_WAIT,
    ENABLING_COMMUNICATION,
    COMMUNICATION_ENABLED,
    PRIMING,
    INITIAL_REAP,
    RUNNING
}
