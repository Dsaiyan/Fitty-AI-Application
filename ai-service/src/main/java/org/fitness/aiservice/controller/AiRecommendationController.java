package org.fitness.aiservice.controller;

import org.fitness.aiservice.dto.RecommendationDTO;
import org.fitness.aiservice.service.AiRecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class AiRecommendationController {
    private final AiRecommendationService aiRecommendationService;

    public AiRecommendationController(AiRecommendationService aiRecommendationService) {
        this.aiRecommendationService = aiRecommendationService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RecommendationDTO>> getRecommendationsByUserId(
            @PathVariable("userId") String userId) {
        return ResponseEntity.ok(aiRecommendationService.getRecommendationsByUserId(userId)) ;
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<RecommendationDTO> getRecommendationsByActivityId(
            @PathVariable("activityId") String activityId) {
        return ResponseEntity.ok(aiRecommendationService.getRecommendationsByActivityId(activityId)) ;
    }

}
