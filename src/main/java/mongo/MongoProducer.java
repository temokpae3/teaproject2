package mongo;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.eclipse.microprofile.config.inject.ConfigProperty;


@ApplicationScoped
public class MongoProducer {

    String uri = "/";



    @Produces
    public MongoClient createMongo() {
        MongoClientURI mongoClientURI = null;
        try {
            MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
//        builder.build();
            mongoClientURI = new MongoClientURI(uri, builder);
        } catch (Exception e) {
            e.printStackTrace();
        }


        assert mongoClientURI != null;
        return new MongoClient(mongoClientURI);

    }

    @Produces
    public MongoDatabase createDB(MongoClient client) {
        return client.getDatabase("posdb");
    }

    public void close(@Disposes MongoClient toClose) {
        toClose.close();
    }
}
