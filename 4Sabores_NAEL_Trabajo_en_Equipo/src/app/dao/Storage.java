package app.dao;

import java.io.*;
import java.util.ArrayList;

public class Storage {
    public static void save(String filename, Object obj) {
        try {
            File dir = new File("data");
            if (!dir.exists()) dir.mkdirs();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/" + filename))) {
                oos.writeObject(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> loadList(String filename) {
        try {
            File f = new File("data/" + filename);
            if (!f.exists()) return new ArrayList<>();
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                Object o = ois.readObject();
                return (ArrayList<T>) o;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
