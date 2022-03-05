package pl.dmcs.idea.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends AccessLevel {

    public Admin() {
        super();
    }
}
