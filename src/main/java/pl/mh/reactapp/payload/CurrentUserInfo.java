package pl.mh.reactapp.payload;

import lombok.Getter;
import lombok.Setter;
import pl.mh.reactapp.domain.Sex;

@Getter
@Setter
public class CurrentUserInfo {
    private Long id;
    private String username;
    private String email;
    private Sex sex;

    public CurrentUserInfo(Long id, String username, String email, Sex sex) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.sex = sex;
    }
}
