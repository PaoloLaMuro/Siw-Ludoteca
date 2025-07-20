package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {



     @Autowired
    protected UserRepository userRepository;


     @Transactional
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }
    
}
