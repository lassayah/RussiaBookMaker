package russiabookmaker.perso.com.russiabookmaker.bet;

import java.util.Date;

import russiabookmaker.perso.com.russiabookmaker.teams.Team;

/**
 * Created by versusmind on 14/09/16.
 */
public class Match {

    private Team team1;
    private Team team2;
    private int resultTeam1;
    private int resultTeam2;
    private int tabTeam1;
    private int tabTeam2;
    private boolean prolongations;
    private Date matchTime;
    private String resultBet;
    private int id;
    private String group;
    private Date dateServeur;
    private String betOk;


    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public int getResultTeam1() {
        return resultTeam1;
    }

    public void setResultTeam1(int resultTeam1) {
        this.resultTeam1 = resultTeam1;
    }

    public int getResultTeam2() {
        return resultTeam2;
    }

    public void setResultTeam2(int resultTeam2) {
        this.resultTeam2 = resultTeam2;
    }

    public int getTabTeam1() {
        return tabTeam1;
    }

    public void setTabTeam1(int tabTeam1) {
        this.tabTeam1 = tabTeam1;
    }

    public int getTabTeam2() {
        return tabTeam2;
    }

    public void setTabTeam2(int tabTeam2) {
        this.tabTeam2 = tabTeam2;
    }

    public boolean isProlongations() {
        return prolongations;
    }

    public void setProlongations(boolean prolongations) {
        this.prolongations = prolongations;
    }

    public Date getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(Date matchTime) {
        this.matchTime = matchTime;
    }

    public String getResultBet() {
        return resultBet;
    }

    public void setResultBet(String resultBet) {
        this.resultBet = resultBet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateServeur() {
        return dateServeur;
    }

    public void setDateServeur(Date dateServeur) {
        this.dateServeur = dateServeur;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getBetOk() {
        return betOk;
    }

    public void setBetOk(String betOk) {
        this.betOk = betOk;
    }

    public String toString() {
        return "match = id : " + id + ", team1 =  " + team1 + ", team2 = " + team2 +
                ", resultBet = " + resultBet;
    }
}
