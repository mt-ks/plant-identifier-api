import com.mtks.PlantAPI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class PlantAPITest {

    PlantAPI plantAPI;

    @BeforeEach
    public void setUp() {
        this.plantAPI = new PlantAPI();
    }

    @Test
    public void shouldBeDefinedPlantApi() {
        Assertions.assertNotNull(plantAPI);
    }

    @Test
    public void shouldGetPlantAttribute() throws ExecutionException, InterruptedException {
        Assertions.assertNotNull(plantAPI.plant);
        CompletableFuture<String> plants = plantAPI.plant.getPlants();
        Assertions.assertNotNull(plants.get());
    }

}
