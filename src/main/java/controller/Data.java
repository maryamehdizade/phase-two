package controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Data {
    private Data INSTANCE;

    private ObjectMapper objectMapper = new ObjectMapper();
    private File file = new File("srever/src/main/java/data/user.json");


    public Data(){
    }

    private void serialize(){
//        try {
//            objectMapper.writeValue(file, null);
//            objectMapper.writeValue(file,);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    private void deserialize(){

    }

    public Data getINSTANCE() {
        if(INSTANCE==null){
            System.out.println("new date");
            INSTANCE = new Data();
        }
        return INSTANCE;
    }
}
