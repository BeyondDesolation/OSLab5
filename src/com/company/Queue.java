package com.company;

public class Queue {

    private int indexHead = -1;
    private int indexTail = -1;

    private int size = 0;
    private Integer[] queue;

    public Queue(){
        queue = new Integer[256];
    }
    public Integer pop(){
        if(size == 0){
            System.out.println("Очередь пуста!");
            return null;
        }
        indexHead++;
        size --;
        if(indexHead == 256){
            indexHead = 0;
        }
        return queue[indexHead];
    }
    public void push(int i){
        if(size == 256){
            System.out.println("Переполнение очереди!");
            return;
        }
        size++;
        indexTail++;
        if(indexTail == 256){
            indexTail = 0;
        }
        queue[indexTail] = i;
    }

    public int getSize() {
        return size;
    }
}
