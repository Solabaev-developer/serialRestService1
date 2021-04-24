package core.parcer;

import core.collector.CollectKinoNewsSerialLinks;
import core.parser.KinoNewsJsoupParser;
import core.parser.KinoNewsParser;
import core.parser.page.GetPage;
import core.parser.page.GetPageImpl;
import core.utils.KinoNewsLinkFormer;
import entity.dto.Serial;
import entity.repo.SerialRepository;
import entity.repo.SerialRepositoryImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Set;

public class TestCollectorLinks {

    private static final SerialRepository repository = new SerialRepositoryImpl();


//    private static Set<Serial> filterSerialsInDB(Set<Serial> serials) {
//        List<Serial> serialsFromDb = repository.getAll();
//
//        Map<String, Serial> serialsMap = new HashMap<>();
//        for (Serial serial : serials) {
//            serialsMap.put(serial.getName(), serial);
//        }
//        serials = null;
//
//        for (Serial serial: serialsFromDb) {
//            serialsMap.remove(serial.getName());
//        }
//
//        return new HashSet<>(serialsMap.values());
//    }

    // Сбор ссылок и парсинг сериалов и сохранение в базу
    public static void main(String[] args) throws IOException {
        Document linksDoc = Jsoup.connect("https://www.kinonews.ru/serials-year2021/").userAgent("Mozilla").get();
        CollectKinoNewsSerialLinks linksCollector = new CollectKinoNewsSerialLinks();

        Set<String> links = linksCollector.collectLinks(linksDoc);

        GetPage<Document> getPage = new GetPageImpl();
        KinoNewsParser<Document> parser;

        int counter = 0;
        System.out.println(links.size());

        for (String link : links) {
            Document mainHtml = getPage.getPage(link);
            String serialId = KinoNewsLinkFormer.getSerialId(link);

            Document seasonsHtml = getPage.getPage(KinoNewsLinkFormer.getSeasonsLink(serialId));
            Document personsHtml = getPage.getPage(KinoNewsLinkFormer.getAllPersonsLink(serialId));

            parser = new KinoNewsJsoupParser(mainHtml, seasonsHtml, personsHtml);
            System.out.println(link);
            Serial serial = parser.parse();
            repository.saveSerial(serial);
        }


//        List<String> links = new ArrayList<>();
//
//        //Ссылки для текущей страницы списка сериалов
//        Elements elements = linksDoc.select("div.zhanr_left > a");
//        for (Element e : elements) {
//            links.add(e.attr("abs:href"));
//        }
//        System.out.println(links);
//        System.out.println(links.size());
    }
}
