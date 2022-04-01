package pl.dmcs.idea.entities;

import lombok.AccessLevel;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.ScaledNumberField;
import pl.dmcs.idea.config.DbNamesConfig;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Indexed
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

    @FullTextField
    private String name;

    @FullTextField
    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ScaledNumberField
    private BigDecimal price;

    private byte[] photo;

    private int amount;

    @OneToMany(mappedBy = "furniture")
    private List<OrderFurniture> orderFurnitureList = new ArrayList<>();
}
