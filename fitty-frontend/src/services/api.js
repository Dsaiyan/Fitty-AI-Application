import axios from "axios";

const API_URL = import.meta.env.VITE_API_BASE_URL;

const api = axios.create(
    {
        baseURL: API_URL,
    }
)

api.interceptors.request.use(config => {
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');
    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
    }
    if(userId){
        config.headers['X-User-Id'] = userId;
    }
    return config;
});

// Get all activities for current user (uses X-User-Id header from interceptor)
export const getAllActivities = () => api.get('/activities');

// Get a single activity by ID
export const getActivityById = (id) => api.get(`/activities/${id}`);

// Get activity details with AI recommendations
export const getActivityDetails = (id) => api.get(`/recommendations/activity/${id}`);

// Add a new activity
export const addActivity = (activity) => api.post('/activities', activity);

