package pl.dmcs.idea.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CLIENT")
public class Client extends AccessLevel {

    public Client() {
        super();
    }
}
