package com.darylmathison.challenge;

import com.hazelcast.core.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Daryl on 3/21/2015.
 */
public class Main {


    public static void main(String args[]) {
        final Properties props = new Properties();
        final HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        try(FileReader properties = new FileReader("target/classes/settings.properties")) {
            props.load(properties);
            final Caller caller = new Caller(props.getProperty("message"), props.getProperty("marker.name"), instance);
            instance.getCluster().addMembershipListener(new MembershipAdapter(){
                @Override
                public void memberAdded(MembershipEvent membershipEvent) {
                    int size = membershipEvent.getCluster().getMembers().size();
                    if(size >= Integer.parseInt(props.getProperty("min.nodes"))) {
                        caller.call();
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
