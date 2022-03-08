package pl.dmcs.idea.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("CLIENT")
public class Client extends AccessLevel {

    public Client() {
        super();
    }

    @OneToMany(mappedBy = "client")
    private List<Order> order = new ArrayList<>();
}
