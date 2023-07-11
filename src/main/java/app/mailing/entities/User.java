package app.mailing.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {
    public Long id;
    public String firstName;
    public String lastName;
    public String email;
    public String externalId;
	public User() {
		super();
	}
}
