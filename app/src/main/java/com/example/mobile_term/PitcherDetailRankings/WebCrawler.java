package com.example.mobile_term.PitcherDetailRankings;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class WebCrawler {public List<BaseballResult> crawlBaseballResults(String url, String category) throws IOException {
    List<BaseballResult> baseballResults = new ArrayList<>();

    try {
        Document document = Jsoup.connect(url).get();
        Elements resultElements = document.select(getSelector(category));

        Log.d("test","result: "+resultElements);

        // 각 li에서 이름, 팀, 승리횟수 추출
        for (int i = 0; i < resultElements.size(); i++) {
            String name = resultElements.get(i).select("a").text();
            String team = resultElements.get(i).select("span.team").text();
            String value = resultElements.get(i).select("em").text();
            BaseballResult baseballResult = new BaseballResult();
            baseballResult.setPlayerName(name);
            baseballResult.setTeamName(team);
            baseballResult.setValue(value);

            baseballResults.add(baseballResult);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return baseballResults;
}
    private String getSelector(String category) {
        switch (category) {
            case "victory":
                return "#content > div.tb_kbo > div > div.tbl_box.p_head > table.pitcher > tbody > tr > td:nth-child(1) > div > ol >li";
            case "era":
                return "#content > div.tb_kbo > div > div.tbl_box.p_head > table.pitcher > tbody > tr > td:nth-child(2) > div > ol >li";
            case "so":
                return "#content > div.tb_kbo > div > div.tbl_box.p_head > table.pitcher > tbody > tr > td:nth-child(3) > div > ol >li";
            case "save":
                return "#content > div.tb_kbo > div > div.tbl_box.p_head > table.pitcher > tbody > tr > td:nth-child(4) > div > ol >li";
            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }
    }

}
