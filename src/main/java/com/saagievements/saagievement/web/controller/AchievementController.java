package com.saagievements.saagievement.web.controller;

import com.saagievements.saagievement.dao.AchievementDao;
import com.saagievements.saagievement.dao.IAchievementDao;
import com.saagievements.saagievement.model.Achievement;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Api( description = "API for CRUD operations on saagievements")
@RestController
public class AchievementController {

    // FIXME use @Autowired
    private IAchievementDao achievementDao;

    public AchievementController() {
        achievementDao = new AchievementDao();
    }

    /**
     * Get all achievements
     * @return achievements list
     */
    @ApiOperation(value = "Get all saagievements")
    @GetMapping(value="/api/achievements")
    public List<Achievement> getAchievements() {
        return achievementDao.findAll();
    }

    /**
     * Get an achievement
     * @param id unique integer identifier
     * @return an achievement
     */
    @ApiOperation(value = "Get a saagievement by id")
    @GetMapping(value="/api/achievement/{id}")
    public Achievement getAnAchievement(@PathVariable int id) {
        return achievementDao.findById(id);
    }

    /**
     * Add a new achievement
     * @param payload a payload like {"goal": "this is a new achievement"}
     */
    @ApiOperation(value = "Add a new saagievement with a goal description")
    @PostMapping(value="/api/achievement")
    public ResponseEntity<Void> addAchievement(@RequestBody Map<String, String> payload) {
        System.out.println(payload);

        String goal = payload.get("goal");
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

    /**
     * Unlock a achievement
     * @param id unique achievement identifier
     * @return an unlocked achievement
     */
    @ApiOperation(value = "Unlock a saagievement")
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
