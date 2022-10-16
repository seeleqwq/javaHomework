package Assignment1;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

import com.alibaba.excel.util.ListUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.select.Elements;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class WriteExcel {
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("性别")
    private String sex;
    @ExcelProperty("职称")
    private String title;
    @ExcelProperty("研究方向")
    private String research;
    @ExcelIgnore
    private String ignore;

    private List<WriteExcel> data(){
        SetVector vector = new SetVector();
        List<WriteExcel> list = ListUtils.newArrayList();
        FetchWhu fetchWhu = new FetchWhu("log");
        try {
            fetchWhu.start(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
//            for (int i = 0; i < fetchWhu.getVector().size(); i++) {
//                WriteExcel data = new WriteExcel();
//                data.setName(((Elements) fetchWhu.getVector().elementAt(i)).eq(0).text());
//                data.setSex(((Elements) fetchWhu.getVector().elementAt(i)).eq(1).text());
//                data.setTitle(((Elements) fetchWhu.getVector().elementAt(i)).eq(2).text());
//                data.setResearch(((Elements) fetchWhu.getVector().elementAt(i)).eq(3).text());
//                list.add(data);
//            }
        FetchHust fetchHust = new FetchHust("log");
        try {
            fetchHust.start(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
//            for (int i = 0; i < fetchHust.getVector().size(); i++) {
//                WriteExcel data = new WriteExcel();
//                data.setName(((Elements) fetchHust.getVector().elementAt(i)).eq(0).text());
//                String sex = ((Elements) fetchHust.getVector().elementAt(i)).eq(1).text().replace("性别：", "");
//                data.setSex(sex);
//                data.setTitle(((Elements) fetchHust.getVector().elementAt(i)).eq(2).text());
//                data.setResearch(((Elements) fetchHust.getVector().elementAt(i)).eq(3).text());
//                list.add(data);
//            }
        FetchSdu fetchSdu = new FetchSdu("log");
        try {
            fetchSdu.start(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
//            for (int i = 0; i < fetchSdu.getVector().size(); i++){
//                WriteExcel data = new WriteExcel();
//                data.setName(((Elements)fetchSdu.getVector().elementAt(i)).eq(0).text());
//                data.setSex("保密");
//                data.setTitle(((Elements)fetchSdu.getVector().elementAt(i)).eq(1).text());
//                data.setResearch(((Elements)fetchSdu.getVector().elementAt(i)).eq(2).text());
//                list.add(data);
//            }
        for (int i = 0; i < vector.vector().size(); i++){
            WriteExcel data = new WriteExcel();
            int j = 0;
            if (((Elements)vector.vector().elementAt(i)).size()<4){
                j = 1;
            }else {
                j = 0;
            }
            data.setName(((Elements)vector.vector().elementAt(i)).eq(0).text());
            if (((Elements)vector.vector().elementAt(i)).eq(1).text().contains("性别")){
                String sex= ((Elements)vector.vector().elementAt(i)).eq(1).text().replace("性别：","");
                data.setSex(sex);
            }else if (j==0){
                data.setSex(((Elements)vector.vector().elementAt(i)).eq(1).text());
            }else {
                data.setSex("保密");
            }
            data.setTitle(((Elements)vector.vector().elementAt(i)).eq(2-j).text());
            data.setResearch(((Elements)vector.vector().elementAt(i)).eq(3-j).text());
            list.add(data);
        }
        return list;
//        List<WriteExcel> list = ListUtils.newArrayList();
//        FetchData fetchData = new FetchData("log");
//        fetchData.setResumable(false);
//        try {
//            fetchData.start(3);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        for (int i = 0; i < fetchData.getVector().size(); i++){
//            WriteExcel data = new WriteExcel();
//            data.setName(((Elements)fetchData.getVector().elementAt(i)).eq(0).text());
//            if (((Elements)fetchData.getVector().elementAt(i)).eq(1).text().contains("性别")){
//                String sex= ((Elements)fetchData.getVector().elementAt(i)).eq(1).text().replace("性别：","");
//                data.setSex(sex);
//            }else {
//                data.setSex(((Elements)fetchData.getVector().elementAt(i)).eq(1).text());
//            }
//            data.setTitle(((Elements)fetchData.getVector().elementAt(i)).eq(2).text());
//            data.setResearch(((Elements)fetchData.getVector().elementAt(i)).eq(3).text());
//            list.add(data);
//        }
//        return list;
    }

    public void simpleWrite(){
        EasyExcel.write("test.xlsx",WriteExcel.class).sheet("test").doWrite(data());
    }

    public static void main(String[] args) {
        WriteExcel test = new WriteExcel();
        test.simpleWrite();
    }

}
