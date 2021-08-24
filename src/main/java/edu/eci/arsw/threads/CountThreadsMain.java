/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */
public class CountThreadsMain {
    
    public static void main(String a[]){
        CountThread Th1 = new CountThread(0,99);
        CountThread Th2 = new CountThread(99,199);
        CountThread Th3 = new CountThread(200,299);
        Th1.run();
        Th2.run();
        Th3.run();
    }
    
}
