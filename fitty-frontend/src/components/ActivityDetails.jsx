import React, {useEffect, useState} from "react";
import {useParams} from "react-router";
import {getActivityDetails} from "../services/api.js";
import {Box, Card, CardContent, Divider, Typography} from "@mui/material";

const ActivityDetails = () => {
//    console.log('ActivityDetails rendered');
    const {id} = useParams();
    const [activity, setActivity] = useState(null) ;
    const [recommendation, setRecommendation] = useState(null) ;

    useEffect(() => {
        const fetchActivityDetails = async()=>{
            try {
                // Fetch recommendation data from /recommendations/activity/{id}
                const response = await getActivityDetails(id);
                const data = response.data;
                
                // The response is a RecommendationDTO which includes activity info
                setRecommendation(data);
                
                // Set activity info (you might need to adjust based on actual response structure)
                setActivity({
                    id: data.activityId,
                    type: data.activityType,
                    userId: data.userId,
                    createdAt: data.createdAt
                });
            }catch (Exception){
                console.error("Error fetching activity details:", Exception);
            }
        }

        fetchActivityDetails().then(() =>  (console.log("Activity details fetched")) );
    }, [id]);

    if( !recommendation){
        return <div>Loading...</div>;
    }
    return (
        <>
            <Box sx={{maxWidth: 800, mx: 'auto', p: 2}}>
                <Card sx={{mb:2}} >
                    <CardContent>
                        <Typography variant="h5" gutterBottom>Activity Details</Typography>
                        <Typography variant="body1"><strong>Type:</strong> {recommendation.activityType}</Typography>
                        <Typography variant="body2" color="text.secondary">
                            Date: {new Date(recommendation.createdAt).toLocaleString()}
                        </Typography>
                    </CardContent>
                </Card>

                <Card>
                    <CardContent>
                        <Typography variant="h5" gutterBottom color="primary">
                            🤖 AI Recommendation
                        </Typography>
                        
                        <Typography variant="h6" sx={{mt: 2, mb: 1}}>📊 Analysis</Typography>
                        <Typography variant="body1" paragraph>
                            {recommendation.recommendationText}
                        </Typography>

                        {recommendation.improvements && recommendation.improvements.length > 0 && (
                            <>
                                <Divider sx={{my:2}} />
                                <Typography variant="h6" sx={{mb: 1}}>💪 Suggested Improvements</Typography>
                                {recommendation.improvements.map((improvement, index) => (
                                    <Typography key={index} variant="body2" sx={{mb: 0.5}}>
                                        • {improvement}
                                    </Typography>
                                ))}
                            </>
                        )}

                        {recommendation.suggestions && recommendation.suggestions.length > 0 && (
                            <>
                                <Divider sx={{my:2}} />
                                <Typography variant="h6" sx={{mb: 1}}>💡 Suggestions</Typography>
                                {recommendation.suggestions.map((suggestion, index) => (
                                    <Typography key={index} variant="body2" sx={{mb: 0.5}}>
                                        • {suggestion}
                                    </Typography>
                                ))}
                            </>
                        )}

                        {recommendation.safetyTips && recommendation.safetyTips.length > 0 && (
                            <>
                                <Divider sx={{my:2}} />
                                <Typography variant="h6" sx={{mb: 1}}>⚠️ Safety Guidelines</Typography>
                                {recommendation.safetyTips.map((guideline, index) => (
                                    <Typography key={index} variant="body2" sx={{mb: 0.5}}>
                                        • {guideline}
                                    </Typography>
                                ))}
                            </>
                        )}
                    </CardContent>
                </Card>
            </Box>
        </>
    )
}

export default ActivityDetails;