/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.spamkeywordsdatasource;

import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
//Thread-safe class
public class HostBlacklistsDataSourceFacade {

//NO TOCAR ESTE CODIGO!!    

    static ConcurrentHashMap<Touple<Integer,String>,Object> blistocurrences=new ConcurrentHashMap<>();

    static{
        Object anyObject=new Object();
        //to be found by a single thread
        blistocurrences.put(new Touple<Integer,String>(23,"200.24.34.55"), anyObject);
        blistocurrences.put(new Touple<Integer,String>(50,"200.24.34.55"), anyObject);
        blistocurrences.put(new Touple<Integer,String>(200,"200.24.34.55"), anyObject);
        blistocurrences.put(new Touple<Integer,String>(1000,"200.24.34.55"), anyObject);
        blistocurrences.put(new Touple<Integer,String>(500,"200.24.34.55"), anyObject);

        //to be found through all threads
        blistocurrences.put(new Touple<Integer,String>(29,"202.24.34.55"), anyObject);
        blistocurrences.put(new Touple<Integer,String>(10034,"202.24.34.55"), anyObject);
        blistocurrences.put(new Touple<Integer,String>(20200,"202.24.34.55"), anyObject);
        blistocurrences.put(new Touple<Integer,String>(31000,"202.24.34.55"), anyObject);
        blistocurrences.put(new Touple<Integer,String>(70500,"202.24.34.55"), anyObject);


        //to be found through all threads
        blistocurrences.put(new Touple<Integer,String>(39,"202.24.34.54"), anyObject);
        blistocurrences.put(new Touple<Integer,String>(10134,"202.24.34.54"), anyObject);
        blistocurrences.put(new Touple<Integer,String>(20300,"202.24.34.54"), anyObject);
        blistocurrences.put(new Touple<Integer,String>(70210,"202.24.34.54"), anyObject);


    }

    private static HostBlacklistsDataSourceFacade instance=new HostBlacklistsDataSourceFacade();

    private Map<String,Integer> threadHits=new ConcurrentHashMap<String,Integer>();

    private String lastConfig=null;

    private int lastIndex=0;

    private HostBlacklistsDataSourceFacade() {

    }


    public static HostBlacklistsDataSourceFacade getInstance(){
        return instance;
    }

    public int getRegisteredServersCount(){
        return 80000;
    }

    public boolean isInBlackListServer(int serverNumber,String ip){

        threadHits.computeIfPresent(Thread.currentThread().getName(), (k, v) -> v + 1);
        threadHits.putIfAbsent(Thread.currentThread().getName(), 1);

        if (System.getProperty("threadsinfo")!=null &&  System.getProperty("threadsinfo").compareToIgnoreCase("true")==0) {
            lastConfig=threadHits.toString();
            lastIndex=serverNumber;
            //System.out.println(threadHits);
        }
        try {
            Thread.sleep(0,1);
        } catch (InterruptedException ex) {
            Logger.getLogger(HostBlacklistsDataSourceFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blistocurrences.containsKey(new Touple<Integer,String>(serverNumber,ip));

    }
    private static final Logger LOG = Logger.getLogger(HostBlacklistsDataSourceFacade.class.getName());

    public void reportAsNotTrustworthy(String host){
        LOG.info("HOST "+host+" Reported as NOT trustworthy");
        if (System.getProperty("threadsinfo")!=null &&  System.getProperty("threadsinfo").compareToIgnoreCase("true")==0) {
            System.out.println("Total threads:"+threadHits.keySet().size());
            System.out.println(lastConfig);
            System.out.println(lastIndex);
        }
    }

    public void reportAsTrustworthy(String host){
        LOG.info("HOST "+host+" Reported as trustworthy");
    }

}

class Touple<T1,T2>{

    T1 firstElement;
    T2 secondElement;

    public Touple(T1 firstElement, T2 secondElement) {
        this.firstElement = firstElement;
        this.secondElement = secondElement;
    }

    public T1 getFirstElement() {
        return firstElement;
    }

    public void setFirstElement(T1 firstElement) {
        this.firstElement = firstElement;
    }

    public T2 getSecondElement() {
        return secondElement;
    }

    public void setSecondElement(T2 secondElement) {
        this.secondElement = secondElement;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.firstElement);
        hash = 79 * hash + Objects.hashCode(this.secondElement);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Touple<?, ?> other = (Touple<?, ?>) obj;
        if (!Objects.equals(this.firstElement, other.firstElement)) {
            return false;
        }
        if (!Objects.equals(this.secondElement, other.secondElement)) {
            return false;
        }
        return true;
    }

}