
package com.example.library.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.example.library.models.Book;
import com.example.library.models.User;
import com.example.library.models.Loan;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class StorageService {

    private final Path folder;
    private final Gson gson;

    public StorageService() {
        folder = Paths.get(System.getProperty("user.home"), ".libraryfx");
        try { Files.createDirectories(folder); } catch (Exception e) { e.printStackTrace(); }
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    private <T> List<T> readList(String filename, Type type) {
        Path p = folder.resolve(filename);
        if (!Files.exists(p)) return new ArrayList<>();
        try (Reader r = Files.newBufferedReader(p)) {
            return gson.fromJson(r, type);
        } catch (Exception e) { e.printStackTrace(); return new ArrayList<>(); }
    }

    private <T> void writeList(String filename, List<T> list) {
        Path p = folder.resolve(filename);
        try (Writer w = Files.newBufferedWriter(p)) {
            gson.toJson(list, w);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<User> loadUsers() {
        Type t = new TypeToken<List<User>>(){}.getType();
        return readList("users.json", t);
    }
    public void saveUsers(List<User> users) { writeList("users.json", users); }

    public List<Book> loadBooks() {
        Type t = new TypeToken<List<Book>>(){}.getType();
        return readList("books.json", t);
    }
    public void saveBooks(List<Book> books) { writeList("books.json", books); }

    public List<Loan> loadLoans() {
        Type t = new TypeToken<List<Loan>>(){}.getType();
        return readList("loans.json", t);
    }
    public void saveLoans(List<Loan> loans) { writeList("loans.json", loans); }

    public Path getBackupFolder() {
        return folder;
    }
}
