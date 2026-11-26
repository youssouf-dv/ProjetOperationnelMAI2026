package sn.unchk.springproject.ressource;

public class InsufficientStockException extends RuntimeException{

    public  InsufficientStockException(int id){
        super("Demande superieur au stock "+id);
    }
}
