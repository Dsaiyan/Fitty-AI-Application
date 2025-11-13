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

export const getActivitiesByUserId = (id) => api.get(`/activities/${id}`) ;
export const getActivityDetails = (id) => api.get(`/recommendations/activity/${id}`) ;
export const addActivity = (activity) => api.post('/activities', activity ) ;

