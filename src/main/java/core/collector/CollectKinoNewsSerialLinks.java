package core.collector;


import core.parser.page.GetPage;
import core.parser.page.GetPageImpl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class CollectKinoNewsSerialLinks implements ParseSerialLinks<Document> {

    private static final GetPage<Document> getPage = new GetPageImpl();
    private static final String TITLE_THIS_YEAR = "div.block-page-new h1";
    private static final String THIS_YEAR_REGEX = "\\d+\\S";
    private static final String NEXT_BUTTON = "https://www.kinonews.ru/images2/page-right-active.png";
    private static final int FINAL_YEAR_FOR_PARSE = 2022;

    private String getCurrentYear(Document html) {
        Elements years = html.select(TITLE_THIS_YEAR);
        String currentYear = years.first().text();
        Pattern yearPattern = Pattern.compile(THIS_YEAR_REGEX);
        Matcher yearMatcher = yearPattern.matcher(currentYear);
        while (yearMatcher.find()) {
            currentYear = yearMatcher.group(0);
        }
        return currentYear;
    }

    @Override
    public Set<String> collectLinks(Document html) {

        Set<String> allLinks = new HashSet<>();
        int currentYear = parseInt(getCurrentYear(html));

        Document currentHtml = html;
        while (currentYear < FINAL_YEAR_FOR_PARSE) {
            allLinks.addAll(getCurrentYearSerialLinks(currentHtml));
            String nextYearLink = getNextYearLink(currentHtml);
            currentYear++;
            currentHtml = getPage.getPage(nextYearLink);
        }
        return allLinks;
    }

    private Set<String> getCurrentYearSerialLinks(Document html) {
        Set<String> links = new HashSet<>();

        Elements elements = html.select("div.zhanr_left > a");
        for (Element e : elements) {
            links.add(e.attr("abs:href"));
        }

        Elements img = html.select("li.img-page > a > img");
        if (img.size() > 0) {
            String imageButton = img.first().attr("abs:src");
            if (NEXT_BUTTON.equals(imageButton)) {
                Elements nextPageLinkElements = html.select("li.img-page > a");
                if (elements.size() > 0) {
                    Document nextHtml = getPage.getPage(nextPageLinkElements.get(0).attr("abs:href"));
                    links.addAll(getCurrentYearSerialLinks(nextHtml));
                }
            }
        }
        return links;
    }

    private String getNextYearLink(Document html) {
        Elements nextYearLinks = html.select("div.block-page-new a:eq(1)");
        String nextYearLink = nextYearLinks.attr("abs:href");
        return nextYearLink;
    }
}

