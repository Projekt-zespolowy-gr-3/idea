package pl.dmcs.idea.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    @Size(min = 1, max = 32)
    @Pattern(regexp = "[a-zA-Z0-9!@$^&*]+")
    private String login;

    @Size(min = 8)
    private String password;

    @Size(min = 1, max = 32)
    @Pattern(regexp = "[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+")
    private String name;

    @Size(min = 1, max = 32)
    @Pattern(regexp = "[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+")
    private String surname;

    @Pattern(regexp = "[a-zA-Z0-9_]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}")
    private String email;
    private boolean active;
    private List<String> accessLevels;
}
