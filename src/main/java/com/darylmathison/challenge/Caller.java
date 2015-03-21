package com.darylmathison.challenge;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.IFunction;

import java.io.Serializable;

/**
 * Created by Daryl on 3/21/2015.
 */
public class Caller {
    public static final long FLAG = -1;

    private IAtomicLong marker;
    private String message;

    public Caller(String message, String markerName, HazelcastInstance instance) {
        marker = instance.getAtomicLong(markerName);
        this.message = message;
    }

    public void call() {
        marker.alter(new CallFunction(message));
    }

    private static class CallFunction implements IFunction<Long, Long>, Serializable {

        private String message;

        public CallFunction(String message) {
            this.message = message;
        }

        @Override
        public Long apply(Long input) {
            if(input != FLAG) {
                System.out.println(message);
            }
            return FLAG;
        }
    }
}
