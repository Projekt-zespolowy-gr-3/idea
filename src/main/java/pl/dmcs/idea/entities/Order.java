package pl.dmcs.idea.entities;

import lombok.*;
import lombok.AccessLevel;
import pl.dmcs.idea.config.DbNamesConfig;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = DbNamesConfig.ORDER_TABLE, schema = DbNamesConfig.ADMIN_SCHEMA)
public class Order {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String businessKey;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderFurniture> orderFurnitureList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private LocalDateTime date;
}
