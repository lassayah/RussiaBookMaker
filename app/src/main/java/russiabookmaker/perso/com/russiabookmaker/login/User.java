package russiabookmaker.perso.com.russiabookmaker.login;

/**
 * Created by versusmind on 06/09/16.
 */
public class User {

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    private String login;

    private boolean isLoggedIn;

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}
