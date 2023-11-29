package com.example.mobile_term.GameResult;

public class DataModel {
    private String rank;
    private String teamName;
    private String plays;
    private String win;
    private String lose;
    private String draw;

    public String getRank() {
        return rank;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getPlays() {
        return plays;
    }

    public String getWin() {
        return win;
    }

    public String getLose() {
        return lose;
    }

    public String getDraw() {
        return draw;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setPlays(String plays) {
        this.plays = plays;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public void setLose(String lose) {
        this.lose = lose;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }

    public DataModel(String rank, String teamName, String plays, String win, String lose, String draw) {
        this.rank = rank;
        this.teamName = teamName;
        this.plays = plays;
        this.win = win;
        this.lose = lose;
        this.draw = draw;

    }}