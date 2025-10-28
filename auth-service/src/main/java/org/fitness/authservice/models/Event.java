package org.fitness.authservice.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Data
@Getter
@Setter
public class Event {
    private String id;
    private long time;
    private String type;
    private String realmId;
    private String realmName;
    private String clientId;
    private String userId;
    private String sessionId;
    private String ipAddress;
    private String error;
    private Map<String, String> details;
}

/*
  proper event structure coming from browser at the time of token generation
  "id": "b1382a64-e2a5-4ffb-8dd9-95f03e976955",
  "time": 1761632816172,
  "type": "REGISTER",
  "realmId": "c12e8740-8b1f-4365-9328-96cfec783fec",
  "realmName": "fitty-oauth-service",
  "clientId": "oauth-pkce-client",
  "userId": "7d8346c3-6e06-4585-bc0e-a0da568131f9",
  "sessionId": null,
  "ipAddress": "172.18.0.1",
  "error": null,
  "details": {
    "auth_method": "openid-connect",
    "auth_type": "code",
    "register_method": "form",
    "last_name": "zoro",
    "redirect_uri": "http://localhost:5173",
    "first_name": "roronoa",
    "code_id": "da97695d-081c-3921-b2c0-fc2749304bb8",
    "email": "zoro@gmail.com",
    "username": "zoro"
  }
}
 */