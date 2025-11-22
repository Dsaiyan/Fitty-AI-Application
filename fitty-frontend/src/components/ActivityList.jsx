import React, {useEffect, useState} from "react";
import {Card, CardContent, Grid, Typography} from "@mui/material";
import {useNavigate} from "react-router";
import {getAllActivities} from "../services/api.js";



const ActivityList = () => {
//    console.log('ActivityList rendered');
    const [activities, setActivities] = useState([]);
    const navigate = useNavigate() ;

    const fetchActivities = async () => {
        // Fetch activities from the API and update state
        try {
            // The X-User-Id header is automatically added by the axios interceptor
            const response = await getAllActivities();
            setActivities(response.data);

        }catch (Exception){
            console.error("Error fetching activities:", Exception);
        }
    }

    useEffect(() => {
        fetchActivities().then(() => (console.log("Activities fetched") ));
    }, []);

    return (
        <>
            <Grid container spacing={2}>
                {/* Activity list items will go here */}
                {activities.map((activity) => (
                    <Grid item xs={12} sm={6} md={4} key={activity.id}>
                        <Card sx={{cursor:'pointer'}}
                              onClick={()=> navigate(`/activities/${activity.id}`)}>
                            <CardContent>
                                <Typography variant='h6'>{activity.type}</Typography>
                                <Typography variant='body1'>Duration : {activity.duration} mins</Typography>
                                <Typography variant='body1'>Calories Burned : {activity.caloriesBurned} kcal</Typography>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>
        </>
    )
}

export default ActivityList;
