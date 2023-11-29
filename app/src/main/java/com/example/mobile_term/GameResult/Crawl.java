package com.example.mobile_term.GameResult;

import com.example.mobile_term.BaseballResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Crawl {
    public List<TeamResult> crawlBaseballResults() throws IOException {
        List<TeamResult> teamResults = new ArrayList<>();
        String url = "https://sports.news.naver.com/kbaseball/record/index?category=kbo&year=2023";

        try {
            Document document = Jsoup.connect(url).get();
            Elements resultElements = document.select("div.rr_wrap div.tbl_box table tbody#regularTeamRecordList_table tr");

            int i =1;

            for (Element element : resultElements) {
                Elements tdElements = element.select("td");

                // 예외 처리: tdElements가 4개 미만이면 스킵
                if (i ==11) {
                    break;
                }

                String teamName = tdElements.get(0).text(); // 세 번째 td에 팀명이 들어있다고 가정
                String plays = tdElements.get(1).text();
                String win = tdElements.get(2).text();
                String lose = tdElements.get(3).text();
                String draw = tdElements.get(4).text();// 네 번째 td에 평균 타율이 들어있다고 가정

                TeamResult teamResult = new TeamResult();
                teamResult.setRank(String.valueOf(i));
                teamResult.setTeamName(teamName);
                teamResult.setPlays(plays);
                teamResult.setWin(win);
                teamResult.setLose(lose);
                teamResult.setDraw(draw);


                teamResults.add(teamResult);

                i+=1;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teamResults;
    }

}