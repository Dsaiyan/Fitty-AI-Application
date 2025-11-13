import React,{useState} from "react";
import {Box, Button, FormControl, InputLabel, MenuItem, Select, TextField} from "@mui/material";
import {addActivity} from "../services/api.js";


const ActivityForm = ({onActivityAdded}) => {
    //console.log('ActivityForm rendered');
    const [activity,setActivity]= useState({
        type: '',
        duration: '',
        caloriesBurned: '',
        additionalMetrics: {}
    });

    const handleSubmitForm = async (event) => {
        event.preventDefault();
        try {
            const userId = localStorage.getItem('userId');
            const payload = { ...activity, userId };
            await addActivity(payload);
            onActivityAdded();
            setActivity({type: '', duration: '', caloriesBurned: '', additionalMetrics: {}});
            console.log('Activity added successfully:');
        }
        catch (e){
            console.error("Error adding activity:", e);
        }
    }
    return (
        <>
            <Box component="form"
                 sx={{ mb: 4, backgroundColor: 'white', color: 'black' }} onSubmit={handleSubmitForm}>
                <FormControl fullWidth sx={{ mb: 2 }}>
                    <InputLabel>Activity Type</InputLabel>
                    <Select
                        value={activity.type}
                        onChange={(e)=> setActivity({...activity, type: e.target.value})}
                        variant="standard" fullWidth label="Activity Type">
                        <MenuItem value="RUNNING">Running</MenuItem>
                        <MenuItem value="CYCLING">Cycling</MenuItem>
                        <MenuItem value="SWIMMING">Swimming</MenuItem>
                        <MenuItem value="WALKING">Walking</MenuItem>
                        <MenuItem value="YOGA">Yoga</MenuItem>
                        <MenuItem value="WEIGHTLIFTING">Weight Lifting</MenuItem>
                        <MenuItem value="HIIT">HIIT</MenuItem>
                        <MenuItem value="CARDIO">Cardio</MenuItem>
                        <MenuItem value="AEROBICS">Aerobics</MenuItem>
                        <MenuItem value="FULL_BODY_WORKOUT">Full Body Workout</MenuItem>
                        <MenuItem value="STRENGTH_TRAINING">Strength Training</MenuItem>
                        <MenuItem value="PILATES">Pilates</MenuItem>
                        <MenuItem value="MARTIAL_ARTS">Martial Arts</MenuItem>
                        <MenuItem value="ROCK_CLIMBING">Rock Climbing</MenuItem>
                        <MenuItem value="HIKING">Hiking</MenuItem>
                        <MenuItem value="DANCE">Dance</MenuItem>
                        <MenuItem value="OTHER">Other</MenuItem>
                    </Select>
                </FormControl>
                <TextField fullWidth sx={{ mb: 2 }} label="Duration (minutes)"
                           variant="standard" type="number" value={activity.duration}
                           onChange={(e)=> setActivity({...activity, duration: e.target.value})}/>

                <TextField fullWidth sx={{ mb: 2 }} label="Calories Burned"
                           variant="standard" type="number" value={activity.caloriesBurned}
                           onChange={(e)=> setActivity({...activity, caloriesBurned: e.target.value})}/>

                {/*<TextField fullWidth sx={{ mb: 2 }} label="Additional Metrics (JSON format)"*/}
                {/*           variant="standard" multiline minRows={3}*/}
                {/*           value={JSON.stringify(activity.additionalMetrics)}*/}
                {/*           onChange={(e)=> {*/}
                {/*               let parsedMetrics = {};*/}
                {/*               try {*/}
                {/*                   parsedMetrics = JSON.parse(e.target.value);*/}
                {/*                   // eslint-disable-next-line no-unused-vars*/}
                {/*               } catch (error) {*/}
                {/*                   console.error("Invalid JSON format for additional metrics");*/}
                {/*               }*/}
                {/*               setActivity({...activity, additionalMetrics: parsedMetrics});*/}
                {/*           }}/>*/}
                <Button type='submit' variant='contained'
                        color='success'>Add Activity</Button>
            </Box>
        </>
    )
}
export default ActivityForm;
