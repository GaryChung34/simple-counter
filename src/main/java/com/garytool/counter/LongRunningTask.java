package com.garytool.counter;

import java.util.Scanner;

public class LongRunningTask implements Runnable{

    @Override
    public void run() {
        Scanner input = new Scanner(System.in);
        String prompt = "";
        while(!prompt.equals("exit")) {
            System.out.println("long task start running");
            System.out.println("press exit to end ...");
            prompt = input.next();
            if(Thread.interrupted()) {
                return;
            }
        }
    }
}
