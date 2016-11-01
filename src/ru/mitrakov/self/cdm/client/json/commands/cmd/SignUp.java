package ru.mitrakov.self.cdm.client.json.commands.cmd;

import java.util.Objects;
import ru.mitrakov.self.cdm.client.json.commands.Cmd;

/**
 *
 * @author Tommy
 */
public class SignUp extends Cmd {
    public final String name;
    public final String password;
    public final String email;

    public SignUp(String name, String password, String email) {
        super("sup");
        this.name = name;
        this.password = password;
        this.email = email;
    }

    // GENERATED CODE
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.password);
        hash = 53 * hash + Objects.hashCode(this.email);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SignUp other = (SignUp) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SignUp{" + "name=" + name + ", password=" + password + ", email=" + email + '}';
    }
}
