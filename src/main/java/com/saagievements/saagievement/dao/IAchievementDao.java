package com.saagievements.saagievement.dao;

import com.saagievements.saagievement.model.Achievement;

import java.util.List;

public interface IAchievementDao {

    /**
     * Get all achievements
     * @return an achievements list
     */
    public List<Achievement> findAll();

    /**
     * Get an achievement
     * @param id unique integer identifier greater than or equal to 0
     * @return an achievement
     */
    public Achievement findById(int id);

    /**
     * Save an achievement
     * @param newAchievement an achievement not null
     * @return achievement saved
     */
    public Achievement save(Achievement newAchievement);

    /**
     * Create and save a new achievement
     * @param goal an achievement description
     * @return achievement saved
     */
    public Achievement save(String goal);

    /**
     * Unlock an achievement
     * @param id of achievement to unlock
     * @return achievement unlocked
     */
    public Achievement unlock(int id);

}
