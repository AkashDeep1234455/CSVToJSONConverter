package org.example;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.*;

public class Main {
    private static final String CSV_path = "/your file path name";
    private static final String JSON_file = "/your output json file name";

    private static List<HashMap<Integer,String>> readCSVFile(File file) throws IOException{
        List<HashMap<Integer,String>> dataList = new ArrayList<>();
        try(FileReader reader = new FileReader(file);
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())){
            for(CSVRecord record:parser){
                HashMap<Integer,String> dataMap = new HashMap<>();
                String ID = record.get("ID");   //use record.get to get the data you want all data are int the string format 
                int id = Integer.parseInt(questionId);
                dataMap.put(id,"/value for the key");
                dataList.add(dataMap);
            }
        }
        return dataList;
    }

    public static void writeJSON(HashMap<Integer,HashSet<String>> allData){
        ObjectMapper mapper = new ObjectMapper();
        try{
            mapper.writeValue(new File(JSON_file),allData);
            System.out.println("Data Succesfully written to "+ JSON_file);
        } catch (StreamWriteException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        File dir = new File(CSV_path);
        File[] files = dir.listFiles((d,name)->name.endsWith(".csv"));
        if(files!=null){
            List<HashMap<Integer,String>> allData = new ArrayList<>();
            for(File file:files){
                try {
                    allData.addAll(readCSVFile(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            HashMap<Integer,HashSet<String>> map = new HashMap<>();
            for(HashMap<Integer,String> data:allData){
                for(int id:data.keySet()){
                    String name = data.get(id);
                    if(!map.containsKey(id)){
                        HashSet<String> input = new HashSet<>();
                        input.add(name);
                        map.put(id,input);
                    }else{
                        HashSet<String> input = map.get(id);
                        input.add(name);
                        map.put(id,input);
                    }
                }
            }
            writeJSON(map);
        }else{
            System.out.println("no csv file found");
        }
    }
}
