package com.demo.client.service;

import org.springframework.stereotype.Service;

@Service
public class PrintService {
    public void print(String data){
        System.out.println(data);
    }
}
