package core.parcer;

import core.parser.page.GetPage;
import core.parser.page.GetPageImpl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestPsevdo {

    private static final GetPage<Document> getPage = new GetPageImpl();

    public List<String> collectLinks(Document html) {

        List<String> allLinks = new ArrayList<>();
        //year = getCurrentYear()
        int year = 1950;

        Document currentHtml = html;
        while (year < 2020) {
            allLinks.addAll(getCurrentYearSerialLinks(currentHtml));
            String nextYearLink = getNextYearLink(html);
            year++;
            currentHtml = getPage.getPage(nextYearLink);
        }
        return allLinks;
    }

    private List<String> getCurrentYearSerialLinks(Document html){
        List<String> links = new ArrayList<>();

        //Ссылки для текущей страницы списка сериалов
        Elements elements = html.select("div.zhanr_left div:eq(1) a");
        for (Element e : elements) {
            links.add(e.attr("abs:href"));
        }

        //Получение ссылки на следующую страницу
        Elements nextPageLinkElements = html.select("");
        if (nextPageLinkElements.size()>0) {
            Document nextHtml = getPage.getPage(nextPageLinkElements.get(0).text());
            links.addAll(getCurrentYearSerialLinks(nextHtml));
        }
        return links;
    }

    private String getNextYearLink(Document html) {
        return "";
    }

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher("Все сериалы 1950 года");

        while (matcher.find()) {
            String group = matcher.group(0);
            System.out.println(group);
        }
    }
}
