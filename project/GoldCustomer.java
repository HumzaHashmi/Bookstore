/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj;

/**
 *
 * 
 */
public class GoldCustomer extends CustomerStatus{
   
    private String stat;
   
    public GoldCustomer(){
        stat="Gold";
    }
   
   
    @Override
    public void changeStatus(Customer c){
        c.setState(new SilverCustomer());        
    }
    @Override
    public String currentStatus(){
        return stat;
    }
}

