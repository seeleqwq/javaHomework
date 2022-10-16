package Assignment1;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import lombok.SneakyThrows;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.util.Vector;

public class FetchData extends BreadthCrawler {
    Vector vector = new Vector();
    public FetchData(String crawlLog){
        super(crawlLog, true);
        addSeed("http://cs.whu.edu.cn/teacher.aspx?showtype=jobtitle&typename=");
        addRegex("http://cs.whu.edu.cn/teacher.aspx\\?showtype=jobtitle&typename=(%..)+");

        addSeed("http://cs.hust.edu.cn/szdw/jsml/axmpyszmlb.htm","tagList");
        setThreads(1);
    }

    @Override
    public void visit(Page page, CrawlDatums next){
        String contentType = page.contentType();
        if(contentType.contains("html")&&page.matchUrl("http://cs.whu.edu.cn/teacher.aspx\\?showtype=jobtitle&typename=(%..)+")){
            Elements tableRow = page.select("tr");
            tableRow.remove(0);
            for (Element tr : tableRow){
                Elements realTableRow = tr.select("td");
                vector.addElement(realTableRow);
            }
        }
        if (contentType.contains("html")&&page.matchUrl(".*hust.edu.cn/.*")){
            if (page.matchType("tagList")){
                next.add(page.links("div.js_menu li>a"),"content");
            }else if (page.matchType("content")){
                Elements names = page.select("div.js-top.clearfix>div.info>h2");
                Elements messages = page.select("div.blockwhite.Psl-info>div.cont>p");
                Elements researches = page.select("div.blockwhite.Rsh-focus>div.cont");
                Elements realInformation = new Elements();
                for (Element name : names){
                    if (name.toString().contains("h2")){
                        realInformation.add(name);
                    }
                }
                for (Element message : messages){
                    if (message.toString().contains("性别：")){
                        Element sex = message.selectFirst("p");
                        realInformation.add(sex);
                        break;
                    }
                }
                realInformation.add(messages.select("p").first());
                for (Element research : researches) {
                    realInformation.add(research);
                }
                if (realInformation.size()==4){
                    vector.addElement(realInformation);
                }
            }
        }
    }
//    public void testVector(){
//        for (int i = 0; i < vector.size(); i++){
//            if (vector.elementAt(i) instanceof Element){
//                System.out.println(((Element)vector.elementAt(i)).text());
//            }else {
//                for (Element td : ((Elements)vector.elementAt(i))){
//                    System.out.println(td.text());
//                }
//            }
//        }
//    }
    public void testVector() {
        for (int i = 0; i < vector.size(); i++) {
            if (vector.elementAt(i) instanceof Element) {
                System.out.println(((Element) vector.elementAt(i)).text());
            } else {
                for (Element td : ((Elements) vector.elementAt(i))) {
                    System.out.println(td.text());
                }
            }
        }
    }

    public Vector getVector(){
        return vector;
    }

    @SneakyThrows
    public static void main(String[] args) {
        FetchData test = new FetchData("log");
        test.setResumable(false);
        test.start(5);
        test.testVector();
    }
}
