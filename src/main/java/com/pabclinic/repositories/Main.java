package com.pabclinic.repositories;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String pwd = bcryptPasswordEncoder.encode("admin");
        System.out.println(pwd);

        System.out.println(LocalDate.now().toString());
    }


}
