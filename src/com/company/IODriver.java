package com.company;

public class IODriver {


    public boolean sendToIO(char data) throws InterruptedException {
        System.out.println("В устройство ввода-вывода отправлен символ: "+ data);
        Thread.sleep(5);
        return true;
    }

}
