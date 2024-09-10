package authorization;

import models.User;

public class AuthorizationToken {
    String tokenJwt;
    User user;

    public AuthorizationToken() {
    }

    public AuthorizationToken(String tokenJwt, User user) {
        this.tokenJwt = tokenJwt;
        this.user = user;
    }

    public String getTokenJwt() {
        return tokenJwt;
    }

    public User getUser() {
        return this.user;
    }

    public void setTokenJwt(String tokenJwt) {
        this.tokenJwt = tokenJwt;
    }
}
