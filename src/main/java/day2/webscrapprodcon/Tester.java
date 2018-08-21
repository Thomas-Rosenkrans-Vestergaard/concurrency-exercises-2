
package day2.webscrapprodcon;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.jsoup.nodes.Document;

public class Tester {
  
  public static void main(String[] args) throws InterruptedException {

    //The list of URL's that must be processed. This is Q1 in the exercise figure
    ArrayBlockingQueue<String> urls = new ArrayBlockingQueue(40);
    
    urls.add("http://www.fck.dk");
    urls.add("http://www.google.com");
    urls.add("http://politiken.dk");
    urls.add("https://cphbusiness.mrooms.net/");
    urls.add("https://facebook.com/");
    urls.add("https://twitch.tv/");
    urls.add("https://youtube.com/");
    urls.add("https://spotify.com/");
    urls.add("https://reddit.com/");

    ArrayBlockingQueue<Document> producedDocuments = new ArrayBlockingQueue(10);

    DocumentCounter counter = new DocumentCounter();

    ExecutorService es = Executors.newCachedThreadPool();
    //Create and start the four Producers (P1-P4)
    es.execute(new DocumentProducer(urls, producedDocuments, counter));
    es.execute(new DocumentProducer(urls, producedDocuments, counter));
    es.execute(new DocumentProducer(urls, producedDocuments, counter));
    es.execute(new DocumentProducer(urls, producedDocuments, counter));
    
    //Create and start the single Consumer Thead (P1)
    es.execute(new DocumentConsumer(counter, producedDocuments));
    
    es.shutdown();
    es.awaitTermination(5,TimeUnit.SECONDS);
  }
}
