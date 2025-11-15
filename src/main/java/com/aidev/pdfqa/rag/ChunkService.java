package com.aidev.pdfqa.rag;

import org.springframework.stereotype.Service;

@Service
public class ChunkService {

    public void printService(String msg) {
        System.out.println("Msg :" + msg);

        for (int i =0; i< 10; i++){
            System.out.println(msg);
        }
    }

}
