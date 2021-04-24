package core.parcer;

import core.parser.page.GetPage;
import core.parser.page.GetPageImpl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestParseMethods {
    public static void main(String[] args) {
        String url, urlAllPers, urlSeasons;
        url = "https://www.kinonews.ru/serial_301459/gosti-iz-proshlogo";
        urlAllPers = "https://www.kinonews.ru/serial_43078_allperson/";
        urlSeasons = "https://www.kinonews.ru/serial_301459_seasons/";

        GetPage<Document> page = new GetPageImpl();
        Document html = page.getPage(url);
        Document htmlPers = page.getPage(urlAllPers);
        Document htmlSeasons = page.getPage(urlSeasons);

        Elements select = htmlPers.select("div.stramplua:contains(Сценаристы) ~ div").first().select("h3 > a");
        for (Element el : select) {
            System.out.println(el.text());
        }
    }
}
