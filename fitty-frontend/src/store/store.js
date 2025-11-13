import { configureStore } from '@reduxjs/toolkit'
import {authSlice} from "./authSlice.js";

export default configureStore({
    reducer: {
        // Add your reducers here
        auth: authSlice.reducer,
    },
})

export class store {
}