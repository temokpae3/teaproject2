
import com.tea.rest.Jar;
import com.tea.rest.Note;
import org.testng.annotations.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Client;
import java.util.ArrayList;


public class DatabaseTest {

    @Test
    public void testDatabase() {
        Jar jar = new Jar();
        jar.setClosed(true);
        jar.setOpeningTime("2022-04-21T00:00");

        jar.openingTimeCloseJar();
    }
}


