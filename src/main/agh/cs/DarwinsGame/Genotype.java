package agh.cs.DarwinsGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Genotype {
    public int genomeSize = 32;
    public int howManyGenes=8;
    int [] isThereEveryGene = new int[howManyGenes];
    List<Integer> genes = new ArrayList<>(32);

    Genotype(){
        for(int i=0;i<howManyGenes;i++){
            genes.add(i);
        }
        for(int i=howManyGenes;i<genomeSize;i++){
            genes.add(ThreadLocalRandom.current().nextInt(howManyGenes));
        }
        genes.sort(Integer::compareTo);

    }
    Genotype(Animal parent1,Animal parent2){
        Genotype genotype1 = parent1.getGenotype();
        Genotype genotype2 = parent2.getGenotype();
        int firstCut = ThreadLocalRandom.current().nextInt(1, 15);
        int secondCut = ThreadLocalRandom.current().nextInt(firstCut, 30);
        int[] isThereEveryGeneTemp = new int[howManyGenes];
        Arrays.fill(isThereEveryGeneTemp, 0);
        for(int i=0;i<genomeSize;i++){
            if(i<firstCut) isThereEveryGeneTemp[genotype1.genes.get(i)]++;
            else if(i<secondCut) isThereEveryGeneTemp[genotype2.genes.get(i)]++;
            else isThereEveryGeneTemp[genotype1.genes.get(i)]++;
        }
        for(int i=0;i<howManyGenes;i++){
            if(isThereEveryGeneTemp[i]==0){
                List<Integer> moreThanOne = new ArrayList<>();
                for(int j=0;j<howManyGenes;j++){
                    if(isThereEveryGeneTemp[j]>1){
                        moreThanOne.add(j);
                    }
                }
                int random = moreThanOne.get(ThreadLocalRandom.current().nextInt(moreThanOne.size()));
                isThereEveryGeneTemp[random]--;
                isThereEveryGeneTemp[i]++;
            }
        }
        isThereEveryGene = Arrays.copyOf(isThereEveryGeneTemp, howManyGenes);
        for(int i=0;i<howManyGenes;i++){
            while(isThereEveryGeneTemp[i]>0){
                genes.add(i);
                isThereEveryGeneTemp[i]-=1;
            }
        }
    }



    public int getHowManyTimesItShouldTurns(){
        return this.genes.get(ThreadLocalRandom.current().nextInt(genomeSize-1));
    }
}
