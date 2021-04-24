package core.parser.page;

import com.sun.istack.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class GetPageImpl implements GetPage<Document> {

    @Override
    @Nullable
    public Document getPage(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .timeout(0)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
}
