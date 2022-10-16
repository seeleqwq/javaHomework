package Assignment1;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Vector;

public class FetchSdu extends BreadthCrawler {
    SetVector vec = new SetVector();
    public FetchSdu(String crawlLog){
        super(crawlLog,true);
        addSeed("https://www.cs.sdu.edu.cn");
        addRegex("https://www.cs.sdu.edu.cn/szdw1/[a-z]{2,4}.htm");
        setThreads(1);
    }

    @Override
    public void visit(Page page, CrawlDatums next){
        String contentType = page.contentType();
        if(contentType.contains("html")&&page.matchUrl("https://www.cs.sdu.edu.cn/szdw1/[a-z]{2,4}.htm")){
            Elements tableRow = page.select("tr");
            tableRow.remove(0);
            for (Element tr : tableRow){
                Elements realTableRow = tr.select("td");
                vec.vector().addElement(realTableRow);
            }
        }
    }

}
