import React,{useState} from "react";
import {Box, Button, FormControl, InputLabel, MenuItem, Select, TextField} from "@mui/material";
import {addActivity} from "../services/api.js";


const ActivityForm = ({onActivityAdded}) => {
    //console.log('ActivityForm rendered');
    const [activity,setActivity]= useState({
        type: '',
        duration: '',
        caloriesBurned: '',
        additionalData: {}
    });

    const [metricRows, setMetricRows] = useState([{ key: '', value: '' }]);

    const handleAddMetricRow = () => {
        setMetricRows([...metricRows, { key: '', value: '' }]);
    };

    const handleRemoveMetricRow = (index) => {
        const newRows = metricRows.filter((_, i) => i !== index);
        setMetricRows(newRows);
    };

    const handleMetricChange = (index, field, value) => {
        const newRows = [...metricRows];
        newRows[index][field] = value;
        setMetricRows(newRows);
    };

    const handleSubmitForm = async (event) => {
        event.preventDefault();
        try {
            const userId = localStorage.getItem('userId');

            // Convert metricRows array to additionalData object
            const additionalData = {};
            metricRows.forEach(row => {
                if (row.key && row.value !== '') {
                    const numValue = parseFloat(row.value);
                    additionalData[row.key] = isNaN(numValue) ? row.value : numValue;
                }
            });

            const payload = { ...activity, additionalData, userId };
            console.log('Sending payload:', JSON.stringify(payload, null, 2));
            await addActivity(payload);
            onActivityAdded();
            setActivity({type: '', duration: '', caloriesBurned: '', additionalData: {}});
            setMetricRows([{ key: '', value: '' }]);
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

                {/* Additional Metrics Section */}
                <Box sx={{ mb: 2, mt: 3 }}>
                    <InputLabel sx={{ mb: 1, fontWeight: 'bold' }}>Additional Metrics</InputLabel>
                    {metricRows.map((row, index) => (
                        <Box key={index} sx={{ display: 'flex', gap: 2, mb: 2, alignItems: 'center' }}>
                            <TextField
                                label="Name"
                                variant="standard"
                                value={row.key}
                                onChange={(e) => handleMetricChange(index, 'key', e.target.value)}
                                sx={{ flex: 1 }}
                            />
                            <TextField
                                label="Value"
                                variant="standard"
                                type="number"
                                value={row.value}
                                onChange={(e) => handleMetricChange(index, 'value', e.target.value)}
                                sx={{ flex: 1 }}
                            />
                            {metricRows.length > 1 && (
                                <Button
                                    variant="outlined"
                                    color="error"
                                    size="small"
                                    onClick={() => handleRemoveMetricRow(index)}
                                    sx={{ minWidth: '40px' }}
                                >
                                    -
                                </Button>
                            )}
                        </Box>
                    ))}
                    <Button
                        variant="outlined"
                        color="primary"
                        size="small"
                        onClick={handleAddMetricRow}
                        sx={{ mt: 1 }}
                    >
                        + Add Metric
                    </Button>
                </Box>

                <Button type='submit' variant='contained'
                        color='success'>Add Activity</Button>
            </Box>
        </>
    )
}
export default ActivityForm;
