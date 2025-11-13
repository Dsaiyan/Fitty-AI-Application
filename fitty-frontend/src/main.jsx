import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import App from './App'
import store from './store/store.js'
import { Provider } from 'react-redux'
import { AuthProvider } from 'react-oauth2-code-pkce'
import {authConfig}  from './config/authConfig.js'

// As of React 18
const root = ReactDOM.createRoot(document.getElementById('root'))

root.render(
    <AuthProvider authConfig={authConfig} loadingComponent={<div>Loading...</div>}>
        <Provider store={store}>
            <App />
        </Provider>
    </AuthProvider>,
)
