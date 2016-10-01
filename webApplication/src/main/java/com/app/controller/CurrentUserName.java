package com.app.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by andrey on 24.08.16.
 *
 *
 */
public class CurrentUserName {

    public static String getCurrentUserName(){
        String userName = null;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }

        return userName;
    }


    public static void main(String[] a){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pass = "admin";

        System.out.println(passwordEncoder.encode(pass));
    }
}
