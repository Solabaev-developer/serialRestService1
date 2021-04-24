package core.parcer;

import core.parser.KinoNewsJsoupParser;
import core.parser.KinoNewsParser;
import core.parser.page.GetPage;
import core.parser.page.GetPageImpl;
import entity.dto.Season;
import entity.dto.Seria;
import entity.dto.Serial;
import org.jsoup.nodes.Document;

public class TestParseSerial {
    public static void main(String[] args) {
        String url, urlAllPers, urlSeasons;
        url = "https://www.kinonews.ru/serial_299911/gusar";
        urlAllPers = "https://www.kinonews.ru/serial_299911_allperson/";
        urlSeasons = "https://www.kinonews.ru/serial_299911_seasons/";

        GetPage<Document> page = new GetPageImpl();
        Document html = page.getPage(url);
        Document htmlPers = page.getPage(urlAllPers);
        Document htmlSeasons = page.getPage(urlSeasons);

        KinoNewsParser<Document> parser = new KinoNewsJsoupParser(html, htmlSeasons, htmlPers);
        Serial serial = parser.parse();

        System.out.println(serial.getName());
        System.out.println(serial.getDescription());
        System.out.println(serial.getStartYear());
        System.out.println(serial.getFinishYear());
        System.out.println(serial.getFilmCompany());
        System.out.println(serial.getPremiereDate());

        System.out.println();

        for (String s : serial.getCountrySet()) {
            System.out.println(s);
        }

        System.out.println();

        for (String s : serial.getGenreList()) {
            System.out.println(s);
        }

        System.out.println();

        if (serial.getCreatorSet() != null && serial.getCreatorSet().size() > 0) {
            System.out.println("Режиссеры");
            for (String s : serial.getCreatorSet()) {
                System.out.println(s);
            }
        }

        System.out.println();

        if (serial.getScreenwriterSet() != null && serial.getScreenwriterSet().size() > 0) {
            System.out.println("Сценаристы");
            for (String s : serial.getScreenwriterSet()) {
                System.out.println(s);
            }
        }

        System.out.println();

        if (serial.getProducerSet() != null && serial.getProducerSet().size() > 0) {
            System.out.println("Продюссеры");
            for (String s : serial.getProducerSet()) {
                System.out.println(s);
            }
        }

        System.out.println();

        if (serial.getOperatorList() != null && serial.getOperatorList().size() > 0) {
            System.out.println("Операторы");
            for (String s : serial.getOperatorList()) {
                System.out.println(s);
            }
        }

        System.out.println();

        if (serial.getComposerSet() != null && serial.getComposerSet().size() > 0) {
            System.out.println("Композиторы");
            for (String s : serial.getComposerSet()) {
                System.out.println(s);
            }
        }

        System.out.println();

        if (serial.getActorSet() != null && serial.getActorSet().size() > 0) {
            System.out.println("Актеры");
            for (String s : serial.getActorSet()) {
                System.out.println(s);
            }
        }

        if (serial.getSeasonSet() != null && serial.getSeasonSet().size() > 0) {
            for (Season season : serial.getSeasonSet()) {
                System.out.print(season.getNumber());
                System.out.print(" ");
                System.out.print(season.getSeriesCount());
                System.out.print(" ");
                System.out.print(season.getStartDate());
                System.out.print(" ");
                System.out.println(season.getFinishDate());

                for (Seria seria : season.getSeriaSet()) {
                    System.out.print(seria.getNumber());
                    System.out.print(" ");
                    System.out.print(seria.getName());
                    System.out.print(" ");
                    System.out.println(seria.getDate());
                }
                System.out.println();
            }
        }
    }
}
