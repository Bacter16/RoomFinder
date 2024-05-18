package org.roomfinder.util;

import com.google.gson.Gson;
import org.roomfinder.model.dto.HotelDTO;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class FileReader {

    String fileName;

    public FileReader(String fileName) {
        this.fileName = fileName;
    }

    public List<HotelDTO> ReadFromFile() {
        try {
            java.io.FileReader reader = new java.io.FileReader(fileName);

            Type listType = new TypeToken<List<HotelDTO>>() { }.getType();

            List<HotelDTO> hotels = new Gson().fromJson(reader, listType);

            reader.close();

            return hotels;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
