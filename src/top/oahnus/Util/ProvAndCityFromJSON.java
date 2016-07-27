package top.oahnus.Util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oahnus on 2016/7/27.
 */
public class ProvAndCityFromJSON {
    private String jsonString     = "";

    /**
     * 加载JSON文件
     */
    public void loadJSONFile(){
        try {
            File file = new File("resource/city.txt");
            FileReader reader = new FileReader(file);

            StringBuffer sb = new StringBuffer();
            char c = (char) reader.read();

            /**
             * 此文件添加#作为终止符
             */
            while(c != '#'){
                sb.append(c);
                c = (char) reader.read();
            }
            jsonString = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
System.out.println("###加载JSON文件过程中出错");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getProvs(){
        JSONArray jsonArray = JSONArray.fromObject(jsonString);

        String[] provs = new String[jsonArray.size()];
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject = JSONObject.fromObject(jsonArray.getString(i));
            provs[i] = jsonObject.get("p").toString();
        }

        return provs;
    }

    public String[] getCitys(String prov){
        JSONArray jsonArray = JSONArray.fromObject(jsonString);

        String[] city = new String[]{};
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject = JSONObject.fromObject(jsonArray.getString(i));
            if(jsonObject.get("p").equals(prov)){
                JSONArray array = JSONArray.fromObject(jsonObject.get("c"));
                city = new String[array.size()];
                for(int j=0;j<array.size();j++){
                    JSONObject object = JSONObject.fromObject(array.getString(j));
                    city[j] = object.get("n").toString();
                }
                break;
            }
        }
        return city;
    }
}
