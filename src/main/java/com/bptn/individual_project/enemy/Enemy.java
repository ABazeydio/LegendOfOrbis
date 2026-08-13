package com.bptn.individual_project.enemy;
import com.bptn.individual_project.character.GameCharacter;
import com.bptn.individual_project.character.Race;

/*
 * This abstract class represents an enemy character in the game. 
 * It extends the GameCharacter class and adds additional properties specific to enemies, such as gold and experience rewards.
 */
public abstract class Enemy extends GameCharacter {

    private int goldReward;
    private int xpReward;

    
    public Enemy(String name, int health, int attackPower, Race race, int goldReward, int xpReward) {
        super(name, health, attackPower, race);
        this.goldReward = goldReward;
        this.xpReward = xpReward;
    }

    /*
     * Reward getters read the values passed into the constructor
     */
    public int getGoldReward() {
        return goldReward;
    }

    public int getXpReward() {
        return xpReward;
    }

}
