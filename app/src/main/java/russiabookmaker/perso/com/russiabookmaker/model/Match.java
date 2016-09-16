package russiabookmaker.perso.com.russiabookmaker.model;

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
}
