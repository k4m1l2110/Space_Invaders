package objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;



public class AlienSpawner{

    private ArrayList<Alien> aliens = new ArrayList<>();
    private int score;
    private int waveLength = 5;

    int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public AlienSpawner() {
    }

    private Alien spawnAlien(){

        Alien result = new Alien(0, 0, entity -> entity.moveDown(), new ArrayList<>(Arrays.asList(
            new Component(
                "res/alien/lvl1/basic/body.png", Map.of(
                "health", 100,
                "armor", 0,
                "speed", 1,
                "agility", 5,
                "strength", 5)),
            new Component(
                "res/alien/lvl1/basic/wing.png", Map.of(
                "health", 100,
                "armor", 0,
                "speed", 1,
                "agility", 5,
                "strength", 5))
        )), true);

        return result;
    }

    public void spawnWave(int score) {

        int numAliens = score/100 + getRandomNumber(1,5);
        for (int i = 0; i < numAliens; i++) {

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

            aliens.add(new Alien(i*100, 0, entity -> entity.moveDown(), new ArrayList<>(Arrays.asList(
                new Component(
                    "res/alien/lvl1/"+type+"/body.png", Map.of(
                    "health", 100,
                    "armor", 0,
                    "speed", 1,
                    "agility", 5,
                    "strength", 5)),
                new Component(
                    "res/alien/lvl1/"+type+"/wing.png", Map.of(
                    "health", 100,
                    "armor", 0,
                    "speed", 1,
                    "agility", 5,
                    "strength", 5))
            ))
            , true
            ));

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