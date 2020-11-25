package com.company;

import java.util.ArrayList;

public class Process {

    private int pid;
    private int processorTimeQuantum;
    private boolean block = false;
    private char[] dataForIO;

    public Process(int pid, int quantum){
        this.pid = pid;
        processorTimeQuantum = quantum;
    }

    public void work(int time) throws InterruptedException {
        processorTimeQuantum -= time;
        System.out.println("Процесс "+ pid + ". Время работы: " + time + ". Оставшееся время: "+processorTimeQuantum);
        Thread.sleep(1);
    }
    public char[] getDataForIO(){
        return dataForIO;
    }

    public int getPid() {
        return pid;
    }

    public int getProcessorTimeQuantum() {
        return processorTimeQuantum;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public void setDataForIO(char[] data) {
        dataForIO = data;
    }
}
