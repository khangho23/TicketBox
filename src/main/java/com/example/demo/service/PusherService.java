package com.example.demo.service;

import java.util.Collections;

import org.springframework.stereotype.Service;

import com.pusher.rest.Pusher;

@Service
public class PusherService {

	static final String APP_ID = "1698461";
    static final String KEY    = "3873c56e91f1f59594b7";
    static final String SECRET = "cb9e0745aa2b5886bbcc";
    static final String CLUSTER = "ap1";
    private final Pusher p = new Pusher(APP_ID, KEY, SECRET);
    
    public void realtime(String channel, String event, String message) {
		p.setCluster("ap1");
		p.trigger(channel, event, Collections.singletonMap("message", message));
    }
}
