package com.company;

public class SimulatedAnealing {

    BoardState current;
    BoardState next;
    BoardState best;
    double temperature=10;// the higher the temperature the more runs is required, too high, and we accept to many bad moves
    int count=0;//keep track of how many times the algorithm has run211
    double coolingRate=.999;//the closer to one the cooling rate gets, the more runs can happen
    long totalTime=0;

    //start with the original board
    public SimulatedAnealing(BoardState temp){
        current=temp;
        best= new BoardState(temp.positions);
    }


    //acceptance rate is determined by an equation
    public double acceptanceRate(){

        if(current.numOfCollisions>next.numOfCollisions)
            return 1.0;
        else {
            //return Eulers number raised to value, gives us the probability that we will accept the new state
            return Math.exp((current.numOfCollisions - next.numOfCollisions) / temperature);

        }
    }

    public boolean aneal(){
        //run algortihm while system hasn't cooled yet
        long start = System.currentTimeMillis();
        while(temperature>.0001){//the system will cool once the temperature gets to a certain number
            //if there are no collisions break from the algorithm
            if(current.numOfCollisions==0)
            {
                break;
            }
            next=current.changeRandomPiece();
            //decide whether or not to accept the new state
            double rate = acceptanceRate();
            if(rate>Math.random()){

                current=next;
            }

            if(current.numOfCollisions<best.numOfCollisions){
                best= new BoardState(current.positions);//set the best board to the state that we just found
            }


            temperature*=coolingRate;//adjust the temperature to cool everytime the cycle has been run

            count++;
        }
        long end = System.currentTimeMillis();



        totalTime=end-start;
        return (best.detectColisions()==0);

    }




}
