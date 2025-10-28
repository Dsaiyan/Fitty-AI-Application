package org.fitness.aiservice.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "recommendations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recommendation {
    @Id
    private String id;
    private String activityId;
    private String userId ;
    private String activityType;
    private String recommendationText;
    private List<String> improvements;
    private List<String> suggestions;
    private List<String> safetyTips;
    @CreatedDate
    private LocalDateTime createdAt;
}
