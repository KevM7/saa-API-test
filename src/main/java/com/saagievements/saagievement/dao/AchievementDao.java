package com.saagievements.saagievement.dao;

import com.saagievements.saagievement.model.Achievement;

import java.util.ArrayList;
import java.util.List;

public class AchievementDao implements IAchievementDao {

    public static List<Achievement> achievements = new ArrayList<>();
    static {
        achievements.add(new Achievement(
                0,
                new String("Unlock achievement on click (POST to /api/achievement/{id}/unlock)"),
                false));
        achievements.add(new Achievement(
                1,
                new String("Create a form to add an achievement"),
                false));
        achievements.add(new Achievement(
                2,
                new String("Create new achievement (POST to /api/achievement with payload: {'goal': 'this is a new achievement'})"),
                false));
        achievements.add(new Achievement(
                3,
                new String("Surprise us ;)"),
                false));
    }

    @Override
    public List<Achievement> findAll() {
        return achievements;
    }

    @Override
    public Achievement findById(int id) {
        for (Achievement achievement : achievements) {
            if (achievement.getId() == id) {
                return achievement;
            }
        }
        return null;
    }

    @Override
    public Achievement save(Achievement newAchievement) {
        if (newAchievement != null) {
            achievements.add(newAchievement);
        }
        return newAchievement;
    }

    @Override
    public Achievement save(String goal) {
        if ((goal == null) || goal.isEmpty()) {
            throw new IllegalArgumentException("Method createAndSave : goal description can't be empty");
        }
        Achievement achievementAdded = new Achievement(achievements.size(), goal, false);
        return save(achievementAdded);
    }

    @Override
    public Achievement unlock(int id) {
        Achievement achievementToUnlock = findById(id);

        if (achievementToUnlock != null) {
            achievementToUnlock.setUnlock(true);
        }

        return achievementToUnlock;
    }
}
