package com.example.mobile_term.DetailRankings;

public class BaseballResult {
    private String playerName;
    private String teamName;
    private String value;

    // 생성자, getter 및 setter 메서드 구현

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}