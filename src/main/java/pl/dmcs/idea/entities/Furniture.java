package pl.dmcs.idea.entities;

import lombok.*;
import lombok.AccessLevel;
import pl.dmcs.idea.config.DbNamesConfig;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = DbNamesConfig.FURNITURE_TABLE, schema = DbNamesConfig.ADMIN_SCHEMA)
public class Furniture {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String businessKey;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    private BigDecimal price;

    private byte[] photo;

    private int amount;
}
