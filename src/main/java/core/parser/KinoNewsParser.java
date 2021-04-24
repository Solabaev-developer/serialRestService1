package core.parser;

import core.utils.Configs;
import entity.dto.Serial;

import java.text.SimpleDateFormat;

public abstract class KinoNewsParser<From> implements SerialParser {

    protected From mainHtml;
    protected From seasonsHtml;
    protected From allPersonsHtml;
    protected static final String datePattern = "\\d{2}\\.\\d{2}\\.\\d{4}";
    protected static final SimpleDateFormat dateFormat = new SimpleDateFormat(Configs.getDateFormat());

    KinoNewsParser(From mainHtml, From seasonsHtml, From allPersonsHtml) {
        this.mainHtml = mainHtml;
        this.seasonsHtml = seasonsHtml;
        this.allPersonsHtml = allPersonsHtml;
    }

    abstract void parseName(From html, Serial serial);

    abstract void parseDescription(From html, Serial serial);

    abstract void parseGenres(From html, Serial serial);

    abstract void parseStartYear(From html, Serial serial);

    abstract void parseFinishYear(From html, Serial serial);

    abstract void parseSeasons(From html, Serial serial);

    abstract void parseCreators(From html, Serial serial);

    abstract void parseScreenWriter(From html, Serial serial);

    abstract void parseComposers(From html, Serial serial);

    abstract void parseOperators(From html, Serial serial);

    abstract void parseProducers(From html, Serial serial);

    abstract void parseActors(From html, Serial serial);

    abstract void parseCountries(From html, Serial serial);

    abstract void parsePremiereDate(From html, Serial serial);

    abstract void parseFilmCompany(From html, Serial serial);


    @Override
    public Serial parse() {
        Serial serial = new Serial();
        parseName(mainHtml, serial);
        parseDescription(mainHtml, serial);
        parseGenres(mainHtml, serial);
        parseStartYear(mainHtml, serial);
        parseFinishYear(mainHtml, serial);
        parseSeasons(seasonsHtml, serial);
        parseCreators(allPersonsHtml, serial);
        parseScreenWriter(allPersonsHtml, serial);
        parseComposers(allPersonsHtml, serial);
        parseOperators(allPersonsHtml, serial);
        parseProducers(allPersonsHtml, serial);
        parseActors(allPersonsHtml, serial);
        parseCountries(mainHtml, serial);
        parsePremiereDate(mainHtml, serial);
        parseFilmCompany(mainHtml, serial);
        return serial;
    }
}
