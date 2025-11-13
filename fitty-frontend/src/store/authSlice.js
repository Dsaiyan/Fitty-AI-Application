import { createSlice } from '@reduxjs/toolkit';
import { authConfig} from "../config/authConfig.js";

export const authSlice = createSlice({
    name: 'authReducer',
    initialState: {
        user: (() => {
            const item = localStorage.getItem('user');
            return item ? JSON.parse(item) : null;
        })(),
        token: localStorage.getItem('token') || null,
        userId: localStorage.getItem('userId') || null,
        authConfig: authConfig,
    },
    reducers: {
        setCredentials: (state, action) => {
            const { user, token, userId } = action.payload;
            state.user = user
            state.token = token
            state.userId = userId;
            localStorage.setItem('user', JSON.stringify(user))
            localStorage.setItem('token', token)
            localStorage.setItem('userId', userId)
        },
        logout: (state) => {
            state.user = null
            state.token = null
            state.userId = null
            localStorage.removeItem('user')
            localStorage.removeItem('token')
            localStorage.removeItem('userId')
        },
    },
})

// Action creators are generated for each case reducer function
export const { setCredentials,logout } = authSlice.actions
export default authSlice.reducer