package day2.webscrapprodcon;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

//This is the class template for the four Producer Threads P1-P4 in the exercise figure
public class DocumentProducer implements Runnable
{

    BlockingQueue<String>   urlsToUse;
    BlockingQueue<Document> producedDocuments;
    Document                doc;
    DocumentCounter         counter;

    public DocumentProducer(BlockingQueue<String> urlsToUse, BlockingQueue producedDocuments, DocumentCounter counter)
    {
        this.urlsToUse = urlsToUse;
        this.producedDocuments = producedDocuments;
        this.counter = counter;
    }

    @Override
    public void run()
    {
        boolean moreUrlsToFetch = true;
        while (moreUrlsToFetch) {
            try {

                String url = urlsToUse.poll();
                if (url == null) {
                    moreUrlsToFetch = false;
                    return;
                }

                int index = counter.incrementConsumed();
                doc = Jsoup.connect(url).get();
                while (true) {
                    if (counter.readProduced() == index - 1) {
                        producedDocuments.add(doc);
                        counter.incrementProduced();
                        break;
                    }

                    Thread.sleep(250);
                }
            } catch (InterruptedException e) {
                moreUrlsToFetch = false;
            } catch (Exception ex) {
                Logger.getLogger(DocumentProducer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
