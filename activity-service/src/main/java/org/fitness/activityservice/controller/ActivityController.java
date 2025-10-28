package org.fitness.activityservice.controller;

import lombok.AllArgsConstructor;
import org.fitness.activityservice.dto.ActivityRequestDto;
import org.fitness.activityservice.dto.ActivityResponseDto;
import org.fitness.activityservice.service.ActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activities")
@AllArgsConstructor
public class ActivityController {
    private final ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResponseDto> trackActivity(@RequestBody ActivityRequestDto activityRequest) {
        return ResponseEntity.ok(activityService.trackActivity(activityRequest));
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponseDto>> getAllActivitiesByUser(@RequestHeader("X-User-Id") String id) {
        return ResponseEntity.ok(activityService.getAllActivitiesByUser(id));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponseDto> getActivityById(@PathVariable String id) {
        return ResponseEntity.ok(activityService.getActivityById(id));
    }
}
