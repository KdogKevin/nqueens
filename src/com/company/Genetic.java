package com.company;

import java.util.*;

public class Genetic {


    PriorityQueue<BoardState> population = new PriorityQueue<>();//the population the is being kept track of at that specific moment
    PriorityQueue<BoardState> breeding = new PriorityQueue<>();// the breeding population after the most fit of the nomral population is selected
    PriorityQueue<BoardState> bred = new PriorityQueue<>();// the population after the breeding has completed
    int count=0;
    BoardState best ;
    int populationSize;
    long totalTime=0;


    //need to add new boards to the initial population before we begin

    public void addBoardState(BoardState newState) {
        population.add(newState);
    }


    public BoardState getNextFitBoard() {
        return population.poll();
    }

    //select the top 50% of the population
    public void selection() {

        populationSize=population.size();
        for (int i = 0; i < populationSize/2 ; i++) {
            breeding.add(population.poll());
        }

    }

    //select the two most fit indiividuals and crossover them
    public void breed() {

        BoardState parent1 = breeding.poll();

        BoardState parent2 = breeding.poll();


        int[] child1 = new int[parent1.positions.length];
        int[] child2 = new int[parent2.positions.length];

        Random rand = new Random();

        //determine an index to split half way of each parent
        int index = rand.nextInt(parent1.positions.length - 1);
        index++;


        //index bound to the length-1 to ensure that the number is 0-> 2 minus the length of the board
        //add 1 to the index so that the index is between 0 and length -1

        int[] p1 = parent1.positions;
        int[] p2 = parent2.positions;

        int[] p1first = Arrays.copyOfRange(p1, 0, index);
        int[] p1second = Arrays.copyOfRange(p1, index, p1.length);

        int[] p2first = Arrays.copyOfRange(p2, 0, index);
        int[] p2second = Arrays.copyOfRange(p2, index, p1.length);


        //cobine two arrays

        System.arraycopy(p1first, 0, child1, 0, p1first.length);
        System.arraycopy(p2second, 0, child1, p1first.length, p2second.length);

        System.arraycopy(p2first, 0, child2, 0, p2first.length);
        System.arraycopy(p1second, 0, child2, p2first.length, p1second.length);


        BoardState boardChild1 = new BoardState(child1);
        BoardState boardChild2 = new BoardState(child2);

        //30% chance of either child to mutate
        if(Math.random()<.3){
            boardChild1=boardChild1.changeRandomPiece();
        }
        if(Math.random()<.3){
            boardChild2=boardChild2.changeRandomPiece();
        }

        if (!bred.contains(boardChild1))
        bred.add(boardChild1);

        if (!bred.contains(boardChild2))
        bred.add(boardChild2);
        if (!bred.contains(parent1))
        bred.add(parent1);
        if (!bred.contains(parent2))
        bred.add(parent2);




    }

    //generate the solution until the generation limit has been reached, and if it is not reached, give the best solution that has been found
    public boolean generateSolution(int generationLimit) {
        long start = System.currentTimeMillis();
        for (int i = 0; i <= generationLimit; i++) {
            selection();

            bred.clear();
            while (breeding.size()>=2) {
                breed();

            }
            if (bred.peek().numOfCollisions == 0) {
                break;
            }
            population.clear();

            population.addAll(bred);

            best=population.peek();
            count++;

        }


        long end = System.currentTimeMillis();
        totalTime=end-start;
        return bred.peek().numOfCollisions==0;

    }





}
