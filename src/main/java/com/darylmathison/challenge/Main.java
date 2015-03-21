package com.darylmathison.challenge;

import com.hazelcast.core.*;

/**
 * Created by Daryl on 3/21/2015.
 */
public class Main {
    public static final String NAME_OF_MARKER = "callMe";
    public static final String MESSAGE = "We are started!";
    public static final long FLAG = -1;

    public static void main(String args[]) {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        IAtomicLong marker = instance.getAtomicLong(NAME_OF_MARKER);
        marker.alter(new IFunction<Long,Long>() {
            @Override
            public Long apply(Long input) {
                if(input != FLAG) {
                    System.out.println(MESSAGE);
                }
                return FLAG;
            }
        });
    }
}
