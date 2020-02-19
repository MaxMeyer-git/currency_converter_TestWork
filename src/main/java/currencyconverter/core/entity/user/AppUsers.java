package currencyconverter.core.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "APP_USERS")
@Getter
@Setter
@NoArgsConstructor
public class AppUsers {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "APP_USER_USER_ROLE",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public AppUsers(AppUsers appUsers) {
        this.id = appUsers.getId();
        this.name = appUsers.getName();
        this.password = appUsers.getPassword();
        this.email = appUsers.getEmail();
        this.roles = appUsers.getRoles();


    }

}
