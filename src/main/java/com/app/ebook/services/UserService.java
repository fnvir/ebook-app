package com.app.ebook.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.app.ebook.model.User;
import com.app.ebook.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;
    
    public UserService(UserRepository ur) {
    	userRepository=ur;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
	public Page<User> getUsersPaginated(int page, int size, String sortBy, String orderBy) {
		boolean z=orderBy != null;
		if(z) sortBy="userId";
		Sort s = sortBy != null ? Sort.by(sortBy) : Sort.unsorted();
		return userRepository.findAll(PageRequest.of(page, size,
				z && orderBy.toLowerCase().equals("desc") ? s.descending() : s.ascending()));
	}

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long userId, User userDetails) {
        Optional<User> u = getUserById(userId);
        if (u.isPresent()) {
        	User user=u.get();
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            return userRepository.save(user);
        }
        return null;
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}