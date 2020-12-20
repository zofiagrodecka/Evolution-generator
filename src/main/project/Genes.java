package project;

import java.util.Arrays;
import java.util.Random;


public class Genes {

    public final int GENES_NUMBER = 32;
    public final int DIFFERENT_GENES = 8;

    private int[] genes = new int[GENES_NUMBER];
    private final int[] genesCounter = new int[DIFFERENT_GENES];

    public Genes(int[] genes){
        this.genes = genes;
        countDifferent();
    }

    public Genes(){
        Random random = new Random();
        for(int i=0; i<GENES_NUMBER; i++){
            int randomNumber = random.nextInt(DIFFERENT_GENES);
            genes[i] = randomNumber;
        }
        countDifferent();
        addLacking();
    }

    public String toString(){
        return Arrays.toString(genes);
    }

    @Override
    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Genes))
            return false;
        Genes that = (Genes) other;

        return Arrays.equals(that.genes, this.genes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(genes);
    }

    public int getGene(int index){
        return genes[index];
    }

    public int randomGene(int from, int to){
        Random random = new Random();
        return random.nextInt(to - from) + from;
    }

    public Genes inherit(Genes other){

        int firstDivision = randomGene(0, GENES_NUMBER-1);
        int secondDivision = randomGene(firstDivision+1, GENES_NUMBER);

        //System.out.println("Miejsca podzialow: " + firstDivision + ", " + secondDivision);

        int[] result = new int[GENES_NUMBER];
        copyGenes(result, genes, 0, firstDivision, 0);
        copyGenes(result, other.genes, firstDivision, secondDivision, firstDivision);
        copyGenes(result, genes, secondDivision, GENES_NUMBER, secondDivision);

        //System.out.println(Arrays.toString(result));

        Genes childGenes = new Genes(result);
        childGenes.addLacking();

        //System.out.println(Arrays.toString(childGenes.getGenes()));
        return childGenes;
    }

    private void countDifferent(){
        for(int gene : genes){
            genesCounter[gene]++;
        }
    }

    private void addLacking(){
        for(int j=0; j<DIFFERENT_GENES; j++){
            if(genesCounter[j] == 0){

                int randomNumber = randomGene(0, GENES_NUMBER);
                while (genesCounter[genes[randomNumber]] < 1) {
                    randomNumber = randomGene(0, GENES_NUMBER);
                }
                mutate(randomNumber, j);
            }
        }
        Arrays.sort(genes);
    }

    private void mutate(int index, int value){

        genesCounter[genes[index]]--;
        genes[index] = value;
        genesCounter[genes[value]]++;
    }

    private void copyGenes(int[] result, int[] copiedArray, int from, int to, int iterator){
        for(int j=from; j<to; j++){
            result[iterator] = copiedArray[j];
            iterator ++;
        }
    }
}
