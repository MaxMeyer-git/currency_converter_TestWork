package currencyconverter.core.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "USER_ROLE")
@Setter
@Getter
@NoArgsConstructor
public class Role {

    private static final UUID ADMIN = UUID.fromString("62e50bed-aa00-4671-96c9-d6c1011447c9");
    private static final UUID USER = UUID.fromString("8acfed4b-6ccd-4dbf-9d50-03e6e4bb3cde");
    private static final UUID GUEST = UUID.fromString("c6fbfe22-88a8-4041-848d-2a232ff716ec");

    @Id
    @Column(name = "role_id")

    private UUID roleId;

    @Column(name = "role")
    private String role;

    public static UUID getADMIN() {
        return ADMIN;
    }

    public static UUID getUSER() {
        return USER;
    }

    public static UUID getGUEST() {
        return GUEST;
    }
}
