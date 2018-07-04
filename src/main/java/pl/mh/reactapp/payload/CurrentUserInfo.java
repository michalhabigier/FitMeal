package pl.mh.reactapp.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentUserInfo {
    private Long id;
    private String username;
    private String email;

    public CurrentUserInfo(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
