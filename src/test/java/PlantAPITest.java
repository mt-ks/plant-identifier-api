import com.mtks.PlantAPI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
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
        System.out.println(plants.get());
        Assertions.assertNotNull(plants.get());
    }

    @Test
    public void processImage() throws ExecutionException, InterruptedException, IOException {
        CompletableFuture<String> proc =  plantAPI.imageProcessNet.processImage("/Users/monapp/Desktop/PlantAppAPI/assets/plant.png");
        Assertions.assertNotNull(proc.get());
    }

    @Test
    public void searchDiagnose() throws ExecutionException, InterruptedException {
        CompletableFuture<String> proc =  plantAPI.diagnose.getDiagnose("pests",1);
        String response = proc.get();
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.startsWith("{"));
    }

}
