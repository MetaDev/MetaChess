/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Harald
 */
public class WrongCollectionLengthException extends Exception{
    private int correctLength;

    public WrongCollectionLengthException(int correctLength) {
        super("Wrong length for used collection, correct length is "+correctLength+".");
        this.correctLength = correctLength;
    }
     
}
