package com.bptn.individual_project.combat;

/**
 * Interface contract for fighting entities in Legends of Orbis.
 */
public interface Combatant {
    String getName();
    int getHealth();
    int getMaxHealth();
    boolean isAlive();
    void takeDamage(int amount);
}
