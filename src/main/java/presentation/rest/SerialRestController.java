package presentation.rest;

import entity.dto.Serial;
import entity.repo.SerialRepository;

import static spark.Spark.*;

public class SerialRestController {

    private SerialRepository repository;
    private static String path = "/serial";

    public SerialRestController(SerialRepository repository) {
        this.repository = repository;

        get(path + "/name/:name", (req, res) -> {
            String name = req.params(":name").replace("_", " ");
            Serial serial = repository.getSerialByName(name);
            return serial;
        }, JsonUtil.json());

        get(path.concat("/all"), (req, res) -> {
            return repository.getAll();
        }, JsonUtil.json());

    }
}
