package edu.eci.arsw.threads;

import edu.eci.arsw.blacklistvalidator.HostBlackListsValidator;
import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.LinkedList;

public class BlackListThread extends Thread{

    private String host;
    private int a, b;
    private int ocurrences = 0;
    private int checkedListsCount = 0;
    private HostBlacklistsDataSourceFacade skds;
    private LinkedList<Integer> ocurrencesList = new LinkedList<>();

    public BlackListThread(String host, int a, int b, HostBlacklistsDataSourceFacade skds){
        this.host = host;
        this.a = a;
        this.b = b;
        this.skds = skds;
    }

    public void run(){
        for (int i = a; i <= b; i++){
            checkedListsCount++;
            if (skds.isInBlackListServer(i,host)){
                ocurrences++;
                ocurrencesList.add(i);
            }
        }
    }

    public int getOcurrences(){
        return ocurrences;
    }

    public LinkedList<Integer> getOcurrencesList(){
        return ocurrencesList;
    }

    public int getCheckedListsCount() {
        return checkedListsCount;
    }
}
