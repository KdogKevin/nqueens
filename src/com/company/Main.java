package com.company;

import java.util.Random;


public class Main {

    //simply run the file to come up with the results
    public static void main(String[] args) {

        System.out.println("***** Running Simulated Anealing *****");
        runSimulatedAnealing(500);
        System.out.println();
        System.out.println("***** Running Genetic Algorithm *****");

        runGenetic(50,10000,500);

    }

//generate the random queen state// change to 0-21
    static int[]  genRand(int length){
        int[] temp = new int[length];
        Random rand=new Random();

        for(int i = 0; i < length; i++){
            int random= rand.nextInt(length);
            temp[i]=random;

        }
        return temp;

    }

    static void runSimulatedAnealing(int instances){

        SimulatedAnealing temp;

        int successCounter=0;
        int failCounter=0;
        int totalRuns=0;
        int avgRuns=0;
        long totalTimeOfSucess=0;
        int successPrinted=0;
        for (int i =0; i<instances;i++){
            temp= new SimulatedAnealing(new BoardState(genRand(21)));
            if(temp.aneal()){
                //increment how many runs it takes to get the average success
                totalRuns+=temp.count;
                totalTimeOfSucess+=temp.totalTime;
                successCounter++;
                if(successPrinted<3){
                    System.out.println("this is the "+ successPrinted+" board found with simulated anealing");
                    System.out.println(temp.best);
                    successPrinted++;
                }
            }
            else
            {
                failCounter++;
            }

        }
        avgRuns=totalRuns/successCounter;
        System.out.println("On average it takes simulated anealing "+ avgRuns+" runs to find the solution");
        long avgTime= totalTimeOfSucess/successCounter;
        System.out.println("On average it takes simulated anealing "+ avgTime+" ms to find the solution");

        System.out.println("The simulated anealing algorithm succeeded "+ successCounter+" times");
        System.out.println("The simulated anealing algorithm failed "+ failCounter+ " times");

    }

    static void runGenetic(int populationSize, int maxGenerations, int instances) {

        int totalSuccess=0;
        int totalFail=0;
        long totalTime=0;
        int totalRuns=0;
        int avgRuns;
        int successPrinted=0;
        for (int j = 0; j < instances; j++) {
            Genetic temp = new Genetic();


            for (int i = 0; i < populationSize; i++) {
                temp.addBoardState(new BoardState(genRand(21)));
            }
           if( temp.generateSolution(maxGenerations)){
               totalTime+=temp.totalTime;
               totalSuccess++;
               totalRuns+=temp.count;
               if(successPrinted<3){
                   System.out.println("this is the "+successPrinted+" board found with Genetic algorithm");
                   System.out.println(temp.best);
                   successPrinted++;

               }

            }
            else{
                totalFail++;
           }

        }
        avgRuns=totalRuns/totalSuccess;
        System.out.println("On average it takes the genetic algorithm "+ avgRuns+" generations to find the solution");
        long avgTime= totalTime/totalSuccess;
        System.out.println("On average it takes the genetic "+ avgTime+" ms to find the solution if one is found at all");

        System.out.println("The genetic algorithm succeeded "+ totalSuccess+" times");
        System.out.println("The genetic algorithm failed "+ totalFail+ " times");
    }
}
