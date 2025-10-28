package org.fitness.aiservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fitness.aiservice.dto.RecommendationDTO;
import org.fitness.aiservice.model.Recommendation;
import org.fitness.aiservice.repository.RecommendationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiRecommendationService {
    private final RecommendationRepository recommendationRepository;
//    private final ActivityAIService activityAIService;

    // Utility method to map Recommendation to RecommendationDTO
    private RecommendationDTO mapToDTO(Recommendation recommendation) {
        RecommendationDTO dto = new RecommendationDTO();
        dto.setActivityId(recommendation.getActivityId());
        dto.setUserId(recommendation.getUserId());
        dto.setActivityType(recommendation.getActivityType());
        dto.setRecommendationText(recommendation.getRecommendationText());
        dto.setImprovements(recommendation.getImprovements());
        dto.setSuggestions(recommendation.getSuggestions());
        dto.setSafetyTips(recommendation.getSafetyTips());
        dto.setCreatedAt(recommendation.getCreatedAt());
        return dto;
    }

    // Method to get recommendations by userId
    public List<RecommendationDTO> getRecommendationsByUserId(String userId) {
        // Placeholder implementation
        Optional<List<Recommendation>> recommendationList = recommendationRepository.findByUserId(userId);
        return recommendationList.orElse(List.of()).stream().map(this::mapToDTO).toList();
    }

    // Method to get recommendations by activityId
    public RecommendationDTO getRecommendationsByActivityId(String activityId) {
        Optional<Recommendation> recommendation = recommendationRepository.findByActivityId(activityId);
        return recommendation.map(this::mapToDTO).orElse(null);
    }

    //Method to save a recommendation for an activity
    public void saveRecommendation(Recommendation recommendation){
        Optional<Recommendation> optionalRecommendation =
                Optional.of(recommendationRepository.save(recommendation));
        log.info("Saved Recommendation : {}", optionalRecommendation.get().getId());
    }
}
