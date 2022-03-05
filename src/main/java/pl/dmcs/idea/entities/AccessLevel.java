package pl.dmcs.idea.entities;

import lombok.Data;
import lombok.Setter;
import pl.dmcs.idea.config.DbNamesConfig;

import javax.persistence.*;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = DbNamesConfig.ACCESS_LEVEL_TABLE, schema = DbNamesConfig.ADMIN_SCHEMA)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "access_level")
public class AccessLevel {

    @Id
    @Setter(lombok.AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "access_level", insertable = false, updatable = false)
    private String accessLevel;

    private boolean active;

    @ManyToOne
    private Account account;
}
