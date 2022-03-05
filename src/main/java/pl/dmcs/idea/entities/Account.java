package pl.dmcs.idea.entities;

import lombok.AccessLevel;
import lombok.*;
import pl.dmcs.idea.config.DbNamesConfig;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = DbNamesConfig.ACCOUNT_TABLE, schema = DbNamesConfig.ADMIN_SCHEMA)
public class Account {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String password;

    private String firstname;

    private String lastname;

    private String email;

    private boolean active;

    private String token;

    @Builder.Default
    @OneToMany(mappedBy = "account", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Collection<pl.dmcs.idea.entities.AccessLevel> accessLevels = new ArrayList<>();
}
