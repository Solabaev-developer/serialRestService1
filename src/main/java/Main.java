import entity.repo.SerialRepositoryImpl;
import presentation.rest.SerialRestController;

public class Main {
    public static void main(String[] args) {
        new SerialRestController(new SerialRepositoryImpl());
    }
}
