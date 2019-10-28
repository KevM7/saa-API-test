package com.saagievements.saagievement.dao;

import com.saagievements.saagievement.dao.AchievementDao;
import com.saagievements.saagievement.model.Achievement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;

@SpringBootTest
public class AchievementDaoTest {

    public static List<Achievement> achievementsTest = new ArrayList<>();
    static {
        achievementsTest.add(new Achievement(
                0,
                new String("Unlock achievement on click (POST to /api/achievement/{id}/unlock)"),
                false));
        achievementsTest.add(new Achievement(
                1,
                new String("Create a form to add an achievement"),
                false));
        achievementsTest.add(new Achievement(
                2,
                new String("Create new achievement (POST to /api/achievement with payload: {'goal': 'this is a new achievement'})"),
                false));
        achievementsTest.add(new Achievement(
                6,
                new String("Surprise us ;)"),
                false));
    }

    // FIXME use @Autowired
    private AchievementDao achievementDao;

    public AchievementDaoTest() {
        achievementDao = new AchievementDao();
        achievementDao.achievements = achievementsTest;
    }

    /**
     * Test findAll
     */
    @Test
    public void whenFindAll_thenReturnAllAchievements() {
        // given
        List<Achievement> achievements = null;

        // when
        achievements = achievementDao.findAll();

        // then
        assertEquals(achievements, achievementsTest);
    }

    /**
     * Test findById method
     * Find first element list
     */
    @Test
    public void whenFindByFirstId_thenReturnAchievement() {
        // given
        Achievement achievementTmpTest = achievementsTest.get(0);
        Achievement achievement = new Achievement(
                achievementTmpTest.getId(),
                achievementTmpTest.getGoal(),
                achievementTmpTest.isUnlock()
        );

        // when
        Achievement achievementFound = achievementDao.findById(achievement.getId());

        // then
        // test equals by reference
        assertEquals(achievementFound, achievementTmpTest);
        // test equals by attributes
        assertEquals(achievementFound, achievement);
    }

    /**
     * Test findById method
     * Find last element list
     */
    @Test
    public void whenFindByLastId_thenReturnAchievement() {
        // given
        Achievement achievementTmpTest = achievementsTest.get(achievementsTest.size() - 1);
        Achievement achievement = new Achievement(
                achievementTmpTest.getId(),
                achievementTmpTest.getGoal(),
                achievementTmpTest.isUnlock()
        );

        // when
        Achievement achievementFound = achievementDao.findById(achievement.getId());

        // then
        // test equals by reference
        assertEquals(achievementFound, achievementTmpTest);
        // test equals by attributes
        assertEquals(achievementFound, achievement);
    }

    /**
     * Test findById method
     * Find by an id < 0
     */
    @Test
    public void whenFindByInvalidId_thenReturnNull() {
        // given
        int id = -1;

        // when
        Achievement achievementFound = achievementDao.findById(id);

        // then
        assertNull(achievementFound);
    }

    /**
     * test save(Achievement)
     */
    @Test
    public void whenSaveByObject_thenReturnAchievement() {
        // given
        Achievement newAchievement = new Achievement(7, "This is a new achievement", false);
        int size = achievementsTest.size();

        // when
        Achievement achievementAdded = achievementDao.save(newAchievement);

        // then
        assertEquals(newAchievement, achievementAdded);
        assertEquals(size + 1, achievementsTest.size());
    }

    /**
     * test save(Achievement)
     * Save null
     */
    @Test
    public void whenSaveNull_thenReturnNull() {
        // given
        Achievement newAchievement = null;
        int size = achievementsTest.size();

        // when
        Achievement achievementAdded = achievementDao.save(newAchievement);

        // then
        assertNull(achievementAdded);
        assertEquals(size, achievementsTest.size());
    }

    /**
     * test save(String)
     */
    @Test
    public void whenSaveByString_thenThrowException() {
        // given
        String goal = new String("This is a new achievement");
        int size = achievementsTest.size();

        // when
        Achievement achievementAdded = achievementDao.save(goal);

        // then
        assertEquals(goal, achievementAdded.getGoal());
        assertEquals(size + 1, achievementsTest.size());
        assertFalse(achievementAdded.isUnlock());
    }

    /**
     * test save(String)
     * Save with an empty string
     */
    @Test
    public void whenSaveEmptyString_thenThrowException() {
        // given
        String goal = new String("");
        int size = achievementsTest.size();

        // when
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            achievementDao.save(goal);
        });

        // then
        assertEquals(size, achievementsTest.size());
    }

    /**
     * test save(String)
     * Save with a null string
     */
    @Test
    public void whenSaveNullString_thenReturnAchievement() {
        // given
        String goal = null;
        int size = achievementsTest.size();

        // when
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            achievementDao.save(goal);
        });

        // then
        assertEquals(size, achievementsTest.size());
    }

    /**
     * Test unlock
     * Unlock first achievement from achievement list
     */
    @Test
    public void whenUnlock_thenReturnAchievement() {
        // given
        Achievement achievementToUnlock = achievementsTest.get(0);
        assertFalse(achievementToUnlock.isUnlock());

        // when
        Achievement achievementUnlocked = achievementDao.unlock(achievementToUnlock.getId());

        // then
        assertTrue(achievementToUnlock.isUnlock());
        assertEquals(achievementToUnlock, achievementUnlocked);
    }

    /**
     * Test unlock
     * Unlock with an invalid id
     */
    @Test
    public void whenUnlockInvalidId_thenReturnNull() {
        // given
        int id = -1;

        // when
        Achievement achievementUnlocked = achievementDao.unlock(id + 0);

        // then
        assertNull(achievementUnlocked);
    }

}
