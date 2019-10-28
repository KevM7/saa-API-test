package com.saagievements.saagievement.web.controller;

import com.saagievements.saagievement.dao.AchievementDao;
import com.saagievements.saagievement.dao.IAchievementDao;
import com.saagievements.saagievement.model.Achievement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class AchievementController {

    // FIXME use @Autowired
    private IAchievementDao achievementDao;

    public AchievementController() {
        achievementDao = new AchievementDao();
    }

    /**
     * Get all achievements
     * @return achivelements list
     */
    @GetMapping(value="/api/achievements")
    public List<Achievement> getAchievements() {
        return achievementDao.findAll();
    }

    /**
     * Get an achievement
     * @param id unique integer identifier
     * @return an achievement
     */
    @GetMapping(value="/api/achievement/{id}")
    public Achievement getAnAchievement(@PathVariable int id) {
        return achievementDao.findById(id);
    }

    /**
     * Add a new achievement
     * @param goal an achievement description
     */
    @PostMapping(value="/api/achievement")
    public ResponseEntity<Void> addAchievement(@RequestBody String goal) {
        Achievement achievementAdded = null;

        try {
            achievementAdded = achievementDao.save(goal);
        } catch (Exception e) {
            achievementAdded = null;
        }

        if (achievementAdded == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(achievementAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping(value="/api/achievement/{id}/unlock")
    public ResponseEntity<Void> unlockAchievement(@PathVariable int id) {
        Achievement achievementToUnlock = achievementDao.unlock(id);

        if (achievementToUnlock == null) {
            return ResponseEntity.badRequest().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromPath("/api/achievement/{id}")
                .buildAndExpand(achievementToUnlock.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
