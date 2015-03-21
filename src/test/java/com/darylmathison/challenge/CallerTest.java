package com.darylmathison.challenge;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicLong;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class CallerTest {
    public static final String MARKER_NAME = "test";
    public static final String MESSAGE = "I am here";

    private static HazelcastInstance instance;
    @BeforeClass
    public static void startInstance() {
        instance = Hazelcast.newHazelcastInstance();
    }

    @AfterClass
    public static void shutdownInstance() {
        instance.shutdown();
    }

    @org.junit.Test
    public void testCall() throws Exception {
        Caller caller = new Caller(MESSAGE, MARKER_NAME, instance);
        caller.call();
        IAtomicLong testMarker = instance.getAtomicLong(MARKER_NAME);
        org.junit.Assert.assertEquals(Caller.FLAG, testMarker.get());
    }
}