package core.parcer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.dto.Season;
import entity.dto.Serial;
import entity.repo.SerialRepository;
import entity.repo.SerialRepositoryImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TestConvertObjectToJson {
    public static void main(String[] args) throws JsonProcessingException {
        SerialRepository repository = new SerialRepositoryImpl();
        Serial serial = repository.getSerialByName("Мир дикого запада");
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(df);

        System.out.println(mapper.writeValueAsString(serial));

    }
}
