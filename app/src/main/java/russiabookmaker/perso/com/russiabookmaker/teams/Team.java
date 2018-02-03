package russiabookmaker.perso.com.russiabookmaker.teams;

/**
 * Created by versusmind on 20/10/2016.
 */

public class Team {

    private String name;
    private String flag;
    private int id;

    public Team()
    {

    }

    public Team(String name, String flag, int id)
    {
        this.name = name;
        this.flag = flag;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
