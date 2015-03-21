package com.darylmathison.challenge;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MembershipAdapter;
import com.hazelcast.core.MembershipEvent;

import java.util.ResourceBundle;

/**
 * Created by Daryl on 3/21/2015.
 */
public class Main {


    public static void main(String args[]) {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        final ResourceBundle props = ResourceBundle.getBundle("settings");
        final Caller caller = new Caller(props.getString("message"), props.getString("marker.name"), instance);
        instance.getCluster().addMembershipListener(new MembershipAdapter(){
            @Override
            public void memberAdded(MembershipEvent membershipEvent) {
                int size = membershipEvent.getCluster().getMembers().size();
                if(size >= Integer.parseInt(props.getString("min.nodes"))) {
                    caller.call();
                }
            }
        });

    }
}
