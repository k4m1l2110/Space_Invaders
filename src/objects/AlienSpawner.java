package objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;



public class AlienSpawner{

    private ArrayList<Alien> aliens = new ArrayList<>();
    private int score, gm, waveLength = 5, difficultyLevel;

    int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public AlienSpawner(int gm, int difficulty) {
        this.gm = gm;
        this.difficultyLevel = difficulty;
    }

    private Alien spawnAlien(String type, int x, int y) {

        Alien result = new Alien(x, y, entity -> {
            entity.moveDown();
            entity.moveRight();
            entity.moveRight();
            entity.moveDown();
            entity.moveLeft();
            entity.moveLeft();
        }, new ArrayList<>(Arrays.asList(
            new Component(
                "res/alien/lvl1/"+type+"/body.png", Map.of(
                "health", 20,
                "armor", 0,
                "speed", 1,
                "agility", 5,
                "strength", 5)),
            new Component(
                "res/alien/lvl1/"+type+"/wing.png", Map.of(
                "health", 20,
                "armor", 0,
                "speed", 1,
                "agility", 5,
                "strength", 5))
        )), true);

        return result;
    }

    public void spawnWave(int score) {

        System.out.println(difficultyLevel);

        //Zad pp4
        int numAliens = (getRandomNumber(1,score/1000 +2)) *difficultyLevel;
        System.out.println("Spawning wave of "+numAliens+" aliens");
        for (int i = 0; i < numAliens+1; i++) {

            String type="basic";

            switch(getRandomNumber(0, 3)){
                case 0:
                    type="basic";
                    break;
                case 1:
                    type="health";
                    break;
                case 2:
                    type="damage";
                    break;
                default:
                    break;
            }

            aliens.add(spawnAlien(type, 800/numAliens * i, 0));

        }

    }

    private boolean allAliensRemoved() {
        return aliens.isEmpty();
    }

    public ArrayList<Alien> getAliens() {
        return aliens;
    }

    public void updateScore(int score) {
        this.score = score;
    }
}