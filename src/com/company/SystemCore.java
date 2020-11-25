package com.company;

public class SystemCore {

    private Process[] processes;

    private Queue queueProcessesReady;
    private IODriver driver;

    public SystemCore() {
        processes = new Process[256];
        queueProcessesReady = new Queue();

        driver = new IODriver();
    }

    private void planNoInterrupt() {
        while (true) {
            if(queueProcessesReady.getSize() == 0){
                return;
            }
            int currentPid = queueProcessesReady.pop();
            //

            if (processes[currentPid].getDataForIO() != null) {
                char[] buffer = processes[currentPid].getDataForIO();
                for (int i = 0; i < buffer.length; i++) {
                    try {
                        driver.sendToIO(buffer[i]);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                processes[currentPid].setDataForIO(null);
            }
            //
            if (processes[currentPid].getProcessorTimeQuantum() <= 0) {
                continue;
            }

            if( ! processes[currentPid].isBlock()) {
                try {
                    processes[currentPid].work(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queueProcessesReady.push(currentPid);
        }
    }

    private void planWithInterrupt() {

        while (true) {
            if(queueProcessesReady.getSize() == 0){
                return;
            }
            int currentPid = queueProcessesReady.pop();
            //
            // Вот здесь бы раелизовать вектор прериваний. Сейчас все сломается, если 2 процесса в одно время захотят
            // получить данные. Но думаю для иллюстрации это уже лишнее.
            if (processes[currentPid].getDataForIO() != null) {

                char[] buffer = processes[currentPid].getDataForIO();
                processes[currentPid].setBlock(true);
                processes[currentPid].setDataForIO(null);
                Runnable task = new Runnable() {
                    public void run() {
                        int cp = currentPid;
                        processes[cp].setBlock(true);
                        for (int i = 0; i < buffer.length; i++) {
                            try {
                                driver.sendToIO(buffer[i]);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        // Здесь должно быть ассинхронное прерывание.
                        processes[cp].setBlock(false);
                        Thread.currentThread().interrupt();
                    }
                };
                Thread dr = new Thread(task, "driver");
                dr.start();
            }

            //

            if (processes[currentPid].getProcessorTimeQuantum() <= 0 ) {
               continue;
            }

            if( ! processes[currentPid].isBlock()) {
                try {
                    processes[currentPid].work(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queueProcessesReady.push(currentPid);
        }
    }

    public void start() {
        planWithInterrupt();
    }

    public void createProcess(char[] dataForIO) {
        for (int i = 0; i < processes.length; i++) {
            if (processes[i] == null) {
                processes[i] = new Process(i, 100);
                processes[i].setDataForIO(dataForIO);
                queueProcessesReady.push(i);
                return;
            }
        }
    }
}
