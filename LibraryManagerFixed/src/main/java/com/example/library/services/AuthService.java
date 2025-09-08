
package com.example.library.services;

import com.example.library.models.User;
import java.util.Optional;

public class AuthService {

    private final LibraryService library;

    public AuthService(LibraryService library) {
        this.library = library;
    }

    public Optional<User> authenticate(String username, String password) {
        Optional<User> u = library.findUserByUsername(username);
        if (u.isPresent()) {
            String hash = Utils.hash(password);
            if (hash.equals(u.get().getPasswordHash())) return u;
        }
        return Optional.empty();
    }
}
