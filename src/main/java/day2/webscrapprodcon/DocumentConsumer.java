package day2.webscrapprodcon;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocumentConsumer implements Runnable
{

    DocumentCounter         counter;
    BlockingQueue<Document> producedDocuments;
    Document                doc;

    public DocumentConsumer(DocumentCounter counter, BlockingQueue<Document> producedDocuments)
    {
        this.counter = counter;
        this.producedDocuments = producedDocuments;
    }

    @Override
    public void run()
    {
        boolean  moreDocumentsToConsume = true;
        Document doc;
        int      totalDivs              = 0;
        while (true) {
            try {

                if (counter.readProduced() != counter.readConsumed()) {

                    doc = producedDocuments.poll(200, TimeUnit.MILLISECONDS);
                    if (doc != null) {
                        String   title = doc.title();
                        Elements divs  = doc.select("div");
                        totalDivs += divs.size();
                        System.out.println(title);
                        System.out.println(divs.size());
                    } else {
                        moreDocumentsToConsume = false;
                    }
                } else {
                    System.out.println("Sum of all Divs:" + totalDivs);
                    return;
                }

            } catch (InterruptedException e) {
                moreDocumentsToConsume = false;
            } catch (Exception ex) {
                Logger.getLogger(DocumentConsumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

