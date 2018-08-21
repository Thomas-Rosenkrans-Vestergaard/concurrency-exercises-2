package day2.webscrapprodcon;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

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
        int totalDivs = 0;
        while (true) {
            try {

                if (counter.readProduced() == counter.readConsumed()) {
                    System.out.println("Sum of all Divs:" + totalDivs);
                    return;
                }

                Document doc = producedDocuments.poll(200, TimeUnit.MILLISECONDS);
                if (doc != null) {
                    String   title = doc.title();
                    Elements divs  = doc.select("div");
                    totalDivs += divs.size();
                    System.out.println(title);
                    System.out.println(divs.size());
                }

            } catch (InterruptedException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

}

