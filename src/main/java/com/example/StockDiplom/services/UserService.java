package com.example.StockDiplom.services;

import com.example.StockDiplom.models.Enums.roles.Role;
import com.example.StockDiplom.models.Member;
import com.example.StockDiplom.models.User;
import com.example.StockDiplom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    /*@Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userRepository.findByName(name);
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());
    }


    /*public void saveUserToDB(String login, String password,String name, String surname, String phone , String email){
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setName(name);
        user.setSurname(surname);
        user.setPhone(phone);
        user.setEmail(email);
        userRepository.save(user);
    }*/

    public void saveUserRegister(User user, String username, String name, Boolean active, Map<String, String> form) {
        user.setUsername(username);
        user.setName(name);
        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        user.setActive(active);
        userRepository.save(user);
    }

     public User saveUser(User user) {
         return userRepository.save(user);
     }
    public Model getUsers(Model model) {
        Iterable<User> users = userRepository.findAll();
        return model.addAttribute("users", users);
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow();
    }

    public List<User> getUsersByName(String name) {
        return userRepository.findByName(name);
    }

    public List<User> getUsersByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getUsersByActive(boolean active) {
        return userRepository.findByActive(active);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void userTurnOffActive(User user) {
        user.setActive(false);
        userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public void updateAccount(User user, String email, String password) {
        String userEmail = user.getEmail();

        boolean isEmailWasChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        if (isEmailWasChanged) {
            user.setEmail(email);
        }

        if (!StringUtils.isEmpty(password)) {
            user.setPassword(password);
        }
        userRepository.save(user);
    }

    public User getCurrentUserAuth() {
        User user;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            user = userRepository.findByUsername(username);
            return user;
        }
        else {
            return null;
        }

    }
}
