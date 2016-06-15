package org.unstoppable.projectstack.model;

import java.util.Map;

public class User {
    private String username;
    private String password;
    private Map<Community, Channel> channelMap;
}
