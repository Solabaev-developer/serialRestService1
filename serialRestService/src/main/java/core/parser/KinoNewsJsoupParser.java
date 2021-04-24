package core.parser;

import entity.dto.Season;
import entity.dto.Seria;
import entity.dto.Serial;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class KinoNewsJsoupParser extends KinoNewsParser<Document> {

    public KinoNewsJsoupParser(Document mainHtml, Document seasonsHtml, Document allPersonsHtml) {
        super(mainHtml, seasonsHtml, allPersonsHtml);
    }

    @Override
    void parseName(Document html, Serial serial) {
        Elements elementsFilmClass = html.getElementsByClass("film");
        if (elementsFilmClass.size() > 0) {
            serial.setName(elementsFilmClass.get(0).text());
        }
    }

    @Override
    void parseDescription(Document html, Serial serial) {
        Elements elements = html.getElementsByAttributeValue("itemprop", "description");
        if (elements.size() > 0) {
            serial.setDescription(elements.get(0).text());
        }
    }

    @Override
    void parseGenres(Document html, Serial serial) {
        Elements elements = html.getElementsByAttributeValue("itemprop", "genre");
        if (elements.size() > 0) {
            serial.setGenreList(elements.stream().map(Element::text).collect(Collectors.toSet()));
        }
    }

    @Override
    void parseStartYear(Document html, Serial serial) {
        if (html.select("td:contains(Год начала)").size() > 0) {
            serial.setStartYear(html.getElementsByAttributeValueContaining("href", "serials-year").get(0).text());
        }
    }

    @Override
    void parseFinishYear(Document html, Serial serial) {
        if (html.select("td:contains(Год окончания)").size() > 0) {
            serial.setFinishYear(html.getElementsByAttributeValueContaining("href", "serials-year").get(1).text());
        }
    }

    @Override
    void parseSeasons(Document html, Serial serial) {
        Elements seasonElements = html.getElementsByClass("text");
        if (seasonElements.size() > 0) {

            for (Element seasonElement : seasonElements) {
                serial.getSeasonSet().add(parseSeason(seasonElement, serial));
            }

            serial.setSeasonsCount(serial.getSeasonSet().size());
        }
    }

    @Override
    void parseCreators(Document html, Serial serial) {
        Elements elements = html.select("div.stramplua:contains(Режиссеры) ~ div");
        if (elements.size() > 0) {
            serial.setCreatorSet(elements.first().select("h3 > a").stream().map(Element::text).collect(Collectors.toSet()));
        }
    }

    @Override
    void parseScreenWriter(Document html, Serial serial) {
        Elements elements = html.select("div.stramplua:contains(Сценаристы) ~ div");
        if (elements.size() > 0) {
            serial.setScreenwriterSet(elements.first().select("h3 > a").stream().map(Element::text).collect(Collectors.toSet()));
        }
    }

    @Override
    void parseComposers(Document html, Serial serial) {
        Elements elements = html.select("div.stramplua:contains(Композиторы) ~ div");
        if (elements.size() > 0) {
            serial.setComposerSet(elements.first().select("h3 > a").stream().map(Element::text).collect(Collectors.toSet()));
        }
    }

    @Override
    void parseOperators(Document html, Serial serial) {
        Elements elements = html.select("div.stramplua:contains(Операторы) ~ div");
        if (elements.size() > 0) {
            serial.setOperatorList(elements.first().select("h3 > a").stream().map(Element::text).collect(Collectors.toSet()));
        }
    }

    @Override
    void parseProducers(Document html, Serial serial) {
        Elements elements = html.select("div.stramplua:contains(Продюсеры) ~ div");
        if (elements.size() > 0) {
            serial.setProducerSet(elements.first().select("h3 > a").stream().map(Element::text).collect(Collectors.toSet()));
        }
    }

    @Override
    void parseActors(Document html, Serial serial) {
        Elements elements = html.select("div.stramplua:contains(Актеры) ~ div");
        if (elements.size() > 0) {
            serial.setActorSet(elements.first().select("h3 > a").stream().map(Element::text).collect(Collectors.toSet()));
        }
    }

    @Override
    void parseCountries(Document html, Serial serial) {
        Elements elements = html.select("tr:contains(Страна)");
        if (elements.size() > 0) {
            String countriesData = elements.get(0).text().split("Страна:\\s")[1];
            String[] countriesArr = countriesData.split(",");
            serial.setCountrySet(Arrays.stream(countriesArr).map(String::trim).collect(Collectors.toSet()));
        }
    }

    @Override
    void parsePremiereDate(Document html, Serial serial) {
        Elements elements = html.select("tr:contains(Премьера)");
        if (elements.size() > 0) {
            try {
                serial.setPremiereDate(new java.sql.Date(dateFormat.parse(elements.get(0).text()
                        .split("Премьера:\\s")[1])
                        .getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    void parseFilmCompany(Document html, Serial serial) {
        Elements elements = html.select("tr:contains(Кинокомпания)");
        if (elements.size() > 0) {
            serial.setFilmCompany(elements.get(0).text().split("Кинокомпания:\\s")[1]);
        }
    }

    private Season parseSeason(Element seasonElement, Serial serial) {
        Season season = new Season();
        season.setSerial(serial);
        Elements elements = seasonElement.getElementsByClass("btext14");
        if (elements.size() > 0) {
            String seasonData = elements.get(0).text();
            Pattern seasonPattern = Pattern.compile("(?:СЕЗОН)\\s*(?<number>\\d+)\\s*(?<start>\\S*)\\s*-\\s*(?<finish>\\S*)");
            Matcher seasonMatcher = seasonPattern.matcher(seasonData);

            while (seasonMatcher.find()) {
                season.setNumber(Integer.parseInt(seasonMatcher.group("number")));
                try {
                    String startDate = seasonMatcher.group("start");
                    if (startDate.matches(datePattern))
                        season.setStartDate(new java.sql.Date(dateFormat.parse(startDate).getTime()));
                    String finishDate = seasonMatcher.group("finish");
                    if (finishDate.matches(datePattern))
                        season.setFinishDate(new java.sql.Date(dateFormat.parse(finishDate).getTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            //parseSeria
            Elements elementsByTag = seasonElement.getElementsByTag("tr");
            if (elementsByTag.size() > 0) {
                for (Element seriaElem : elementsByTag) {
                    season.getSeriaSet().add(parseSeria(seriaElem, season));
                }
                season.setSeriesCount(season.getSeriaSet().size());
            }
        }

        return season;
    }

    private Seria parseSeria(Element seriaElement, Season season) {
        Seria seria = new Seria();
        seria.setSeason(season);

        Elements tdElems = seriaElement.getElementsByTag("td");

        if (tdElems.size() > 0) {
            if (tdElems.get(0).text().matches("Серия\\s\\d+")) {
                String numberData = tdElems.get(0).text();
                Integer number = Integer.parseInt(numberData.split(" ")[1]);
                seria.setNumber(number);

                String seriaData = tdElems.get(1).text();
                Pattern pattern = Pattern.compile("(?<name>.+),\\s*(?<date>\\S+)");
                Matcher matcher = pattern.matcher(seriaData);

                while (matcher.find()) {
                    seria.setName(matcher.group("name"));
                    try {
                        String date = matcher.group("date");
                        if (date.matches(datePattern))
                            seria.setDate(new java.sql.Date(dateFormat.parse(date).getTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return seria;
    }
}
