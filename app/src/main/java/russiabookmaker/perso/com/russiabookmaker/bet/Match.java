package russiabookmaker.perso.com.russiabookmaker.bet;

import java.util.Date;

/**
 * Created by versusmind on 14/09/16.
 */
public class Match {

    private String team1;
    private String team2;
    private int resultTeam1;
    private int resultTeam2;
    private int tabTeam1;
    private int tabTeam2;
    private boolean prolongations;
    private Date matchTime;
    private String resultBet;
    private int id;
    private int team1Id;
    private int team2Id;
    private String group;
    private Date dateServeur;
    private String betOk;
    private String flag1;
    private String flag2;


    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
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

    public String getFlag2() {
        return flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public int getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(int team1Id) {
        this.team1Id = team1Id;
    }

    public int getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(int team2Id) {
        this.team2Id = team2Id;
    }

    public String toString() {
        return "match = id : " + id + ", team1 =  " + team1 + ", team2 = " + team2 + ", team1Id =  " + team1Id + ", team2Id = " + team2Id +
                ", resultBet = " + resultBet;
    }
}
