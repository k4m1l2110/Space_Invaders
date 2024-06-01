package objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import javax.swing.*;



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

    private Alien spawnAlien(String type, int x, int y, int stats, boolean sentient){
        if(gm==0)
        return new Alien(x, y, entity -> {

            entity.movementTimer = new Timer(500, e -> {
                entity.moveRL = !entity.moveRL;
            });
            entity.movementTimer.start();
            if(entity.moveRL)
                entity.moveRight();
            else
                entity.moveLeft();
            entity.moveDown();
        }, new ArrayList<>(Arrays.asList(
                new Component(
                        "res/alien/lvl1/"+type+"/body.png", Map.of(
                        "health", 10,
                        "speed", 0,
                        "agility", 1,
                        "attack", 20)),
                new Component(
                        "res/alien/lvl1/"+type+"/wing.png", Map.of(
                        "health", 10,
                        "speed", 1,
                        "agility", 1,
                        "attack", 20))
        )), false);

        Alien result = new Alien(x, y, entity -> {

            entity.movementTimer = new Timer(1000, e -> {
                entity.moveRL = !entity.moveRL;
            });
            entity.movementTimer.start();
            if(entity.moveRL)
                entity.moveRight();
            else
                entity.moveLeft();
            entity.moveDown();
        }, new ArrayList<>(Arrays.asList(
            new Component(
                "res/alien/lvl1/"+type+"/body.png", Map.of(
                "health", type=="health"?20+stats:10+stats,
                    "speed", type=="speed"?2+stats/10:1+stats/10,
                    "agility", type=="speed"?2+stats/10:1+stats/10,
                    "attack", type=="damage"?10+stats:5+stats)),
            new Component(
                "res/alien/lvl1/"+type+"/wing.png", Map.of(
                    "health", type=="health"?20+stats:10+stats,
                "speed", type=="speed"?2+stats/10:1+stats/10,
                    "agility", type=="speed"?2+stats/10:1+stats/10,
                "attack", type=="damage"?10+stats:5+stats))
        )), sentient);

        return result;
    }

    public void spawnWave(int score) {

        System.out.println(difficultyLevel);

        //Zad pp4
        int numAliens = (getRandomNumber(1,score/1000 +2)) *difficultyLevel;

        String type="basic";
        int stat= getRandomNumber(10, 20);
        boolean sentient = gm==1?(getRandomNumber(0, 1)==0):false;

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

        System.out.println("Spawning wave of "+numAliens+" aliens");
        for (int i = 0; i < numAliens*2; i++) {

            aliens.add(spawnAlien(type, 800/numAliens * i, 0, stat, sentient));
            if(gm==1)
            aliens.get(aliens.size()-1).shootDelayTimer.start();

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