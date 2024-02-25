package com.admindashboard.admindashboard.util;

public class SnowflakeIdGenerator {
    private static final long EPOCH = 1609459200000L; // January 1, 2021
    private static final long WORKER_ID_BITS = 5L;
    private static final long DATACENTER_ID_BITS = 5L;
    private static final long SEQUENCE_BITS = 12L;

    private static final long MAX_WORKER_ID = (1L << WORKER_ID_BITS) - 1;
    private static final long MAX_DATACENTER_ID = (1L << DATACENTER_ID_BITS) - 1;
    private static final long MAX_SEQUENCE = (1L << SEQUENCE_BITS) - 1;

    private final long workerId;
    private final long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowflakeIdGenerator(long workerId, long datacenterId) {
        if (workerId < 0 || workerId > MAX_WORKER_ID) {
            throw new IllegalArgumentException("Worker ID must be between 0 and " + MAX_WORKER_ID);
        }
        if (datacenterId < 0 || datacenterId > MAX_DATACENTER_ID) {
            throw new IllegalArgumentException("Datacenter ID must be between 0 and " + MAX_DATACENTER_ID);
        }

        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long generateUniqueId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate ID.");
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                // Sequence overflow, wait for the next millisecond
                timestamp = waitNextMillis(timestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - EPOCH) << (WORKER_ID_BITS + DATACENTER_ID_BITS + SEQUENCE_BITS)) |
                (datacenterId << (WORKER_ID_BITS + SEQUENCE_BITS)) |
                (workerId << SEQUENCE_BITS) |
                sequence;
    }

    private long waitNextMillis(long currentTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= currentTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
