package com.example.mobile_term;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebCrawler {
    public List<BaseballResult> crawlBaseballResults() throws IOException {
        List<BaseballResult> baseballResults = new ArrayList<>();
        String url = "https://www.koreabaseball.com/Record/Player/HitterBasic/BasicOld.aspx?sort=HRA_RT";
        try{
        Document document = Jsoup.connect(url).get();
        Elements resultElements = document.select("#cphContents_cphContents_cphContents_udpContent > div.record_result > table > tbody > tr");

        for (Element element : resultElements) {
            Elements tdElements = element.select("td");

            // 예외 처리: tdElements가 4개 미만이면 스킵
            if (tdElements.size() < 4) {
                continue;
            }

            String playerName = tdElements.get(1).text(); // 두 번째 td에 선수명이 들어있다고 가정
            String teamName = tdElements.get(2).text(); // 세 번째 td에 팀명이 들어있다고 가정
            String avg = tdElements.get(3).text(); // 네 번째 td에 평균 타율이 들어있다고 가정

            BaseballResult baseballResult = new BaseballResult();
            baseballResult.setPlayerName(playerName);
            baseballResult.setTeamName(teamName);
            baseballResult.setAvg(avg);

            baseballResults.add(baseballResult);
        }
            // id 값 설정
            for (int i = 0; i < baseballResults.size(); i++) {
                baseballResults.get(i).setId(i + 1); // 간단히 1부터 순차적으로 id 설정
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baseballResults;
    }

}
