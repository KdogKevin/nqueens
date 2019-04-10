package com.company;

import com.sun.org.apache.xpath.internal.functions.FuncContains;

import java.util.Arrays;

import java.util.Random;

public class BoardState implements Comparable<BoardState> {

    int[] positions;
    boolean solved=false;
    int numOfCollisions=0;
    int fitness;//higher fitness is better


//board starts with an array of the positions of the queens
    public BoardState (int [] queens){

        positions=queens;
        detectColisions();
        fitness=determineFitness();

    }

    //whichever is more fit is higher priority;
    public int compareTo(BoardState other){
        return -Integer.compare(this.fitness, other.fitness);

    }

    public int determineFitness(){
        int min=0;
        int max= positions.length*(positions.length-1)/2;
        return max-numOfCollisions;
    }

    //return new Board state that has one changed state
    public BoardState changeRandomPiece(){
        Random rand = new Random();
        int[] temp =positions.clone();

        temp[rand.nextInt(positions.length)]=rand.nextInt(positions.length);
        return new BoardState(temp);
    }



    //detecting the collision
    public int detectColisions(){
        int count=0;

        for(int i =0; i< positions.length-1;i++){
            int queen1=positions[i];
            for(int j=i+1; j<positions.length;j++){
                int queen2=positions[j];
                if(queen1==queen2){
                    count++;
                }
                else if(i-queen1== j-queen2){//diagonal detection
                    count++;
                }
                else if (queen1+i== queen2+j){//diagonal detection
                    count++;

                }
            }

        }

        numOfCollisions= count;
        return count ;
    }


    public String toString(){
        return Arrays.toString(positions);
    }


}
