package pl.dmcs.idea.entities;

import lombok.*;
import lombok.AccessLevel;
import pl.dmcs.idea.config.DbNamesConfig;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = DbNamesConfig.ORDER_FURNITURE_TABLE, schema = DbNamesConfig.ADMIN_SCHEMA)
public class OrderFurniture {

    @Id
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "furniture_id")
    private Furniture furniture;

    @Column(name = "quantity")
    private Integer quantity;
}
