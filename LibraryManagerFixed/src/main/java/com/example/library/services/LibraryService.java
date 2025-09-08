
package com.example.library.services;

import com.example.library.models.Book;
import com.example.library.models.User;
import com.example.library.models.Loan;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class LibraryService {

    private final StorageService storage;
    private final List<User> users;
    private final List<Book> books;
    private final List<Loan> loans;

    public LibraryService() {
        storage = new StorageService();
        users = new ArrayList<>(storage.loadUsers());
        books = new ArrayList<>(storage.loadBooks());
        loans = new ArrayList<>(storage.loadLoans());

        // create default admin if not exists
        if (users.stream().noneMatch(u -> "admin".equals(u.getUsername()))) {
            users.add(new User("admin", Utils.hash("admin123"), "Administrator", "ADMIN"));
            storage.saveUsers(users);
        }
    }

    // Users
    public Optional<User> findUserByUsername(String username) {
        return users.stream().filter(u->u.getUsername().equals(username)).findFirst();
    }
    public void addUser(User u) { users.add(u); storage.saveUsers(users); }
    public List<User> getAllUsers() { return Collections.unmodifiableList(users); }

    // Books
    public List<Book> getAllBooks() { return Collections.unmodifiableList(books); }
    public void addBook(Book b) { books.add(b); storage.saveBooks(books); }
    public void updateBook(Book b) {
        books.stream().filter(x->x.getId().equals(b.getId())).findFirst().ifPresent(s->{
            s.setTitle(b.getTitle()); s.setAuthor(b.getAuthor()); s.setCategory(b.getCategory()); s.setYear(b.getYear()); s.setCopies(b.getCopies());
        });
        storage.saveBooks(books);
    }
    public void removeBook(String id) { books.removeIf(b->b.getId().equals(id)); storage.saveBooks(books); }

    public List<Book> searchBooks(String q) {
        String qq = q==null? "": q.toLowerCase();
        return books.stream().filter(b->b.getTitle().toLowerCase().contains(qq) || b.getAuthor().toLowerCase().contains(qq) || b.getCategory().toLowerCase().contains(qq)).collect(Collectors.toList());
    }

    // Loans
    public List<Loan> getAllLoans() { return Collections.unmodifiableList(loans); }
    public void addLoan(Loan l) {
        // decrement copy if available
        books.stream().filter(b->b.getId().equals(l.getBookId())).findFirst().ifPresent(b->{ if (b.getCopies()>0) b.setCopies(b.getCopies()-1); });
        loans.add(l);
        storage.saveBooks(books);
        storage.saveLoans(loans);
    }
    public void returnLoan(String loanId, LocalDate returnDate) {
        loans.stream().filter(l->l.getId().equals(loanId)).findFirst().ifPresent(l->{
            l.setReturnDate(returnDate);
            long daysLate = java.time.temporal.ChronoUnit.DAYS.between(l.getDueDate(), returnDate);
            if (daysLate>0) l.setFine(daysLate * 1.0); // 1.0 currency unit per day
            // restore copy
            books.stream().filter(b->b.getId().equals(l.getBookId())).findFirst().ifPresent(b->b.setCopies(b.getCopies()+1));
        });
        storage.saveBooks(books);
        storage.saveLoans(loans);
    }

    // Reports
    public long totalBooks() { return books.stream().mapToInt(Book::getCopies).sum(); }
    public long totalActiveLoans() { return loans.stream().filter(l->l.getReturnDate()==null).count(); }
    public List<User> topUsersByLoans() {
        Map<String, Long> counts = new HashMap<>();
        for (Loan l: loans) counts.put(l.getUserId(), counts.getOrDefault(l.getUserId(),0L)+1);
        return counts.entrySet().stream().sorted((a,b)->Long.compare(b.getValue(), a.getValue())).map(e->findUserById(e.getKey())).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public User findUserById(String id) { return users.stream().filter(u->u.getId().equals(id)).findFirst().orElse(null); }
    public Book findBookById(String id) { return books.stream().filter(b->b.getId().equals(id)).findFirst().orElse(null); }
    public Loan findLoanById(String id) { return loans.stream().filter(l->l.getId().equals(id)).findFirst().orElse(null); }

    public void backup(String filename) {
        try {
            java.nio.file.Files.copy(storage.getBackupFolder().resolve("users.json"), java.nio.file.Paths.get(filename+".users.json"), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
