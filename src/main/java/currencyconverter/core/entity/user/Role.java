package currencyconverter.core.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_role")
@Setter
@Getter
@NoArgsConstructor
public class Role {

    private static final int ADMIN = 1;
    private static final int USER = 2;
    private static final int GUEST = 3;

    @Id
    @Column(name = "role_id")
    private int roleId;

    @Column(name = "role")
    private String role;

}
