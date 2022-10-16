package Assignment1;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Vector;

public class FetchWhu extends BreadthCrawler {
    SetVector vec = new SetVector();
    public FetchWhu(String crawlLog) {
        super(crawlLog, true);
        addSeed("http://cs.whu.edu.cn/teacher.aspx?showtype=jobtitle&typename=");
        addRegex("http://cs.whu.edu.cn/teacher.aspx\\?showtype=jobtitle&typename=(%..)+");
        setThreads(1);
    }

        @Override
        public void visit(Page page, CrawlDatums next){
            String contentType = page.contentType();
            if (contentType.contains("html") && page.matchUrl("http://cs.whu.edu.cn/teacher.aspx\\?showtype=jobtitle&typename=(%..)+")) {
                Elements tableRow = page.select("tr");
                tableRow.remove(0);
                for (Element tr : tableRow) {
                    Elements realTableRow = tr.select("td");
                    vec.vector().addElement(realTableRow);
                }
            }
        }

}
