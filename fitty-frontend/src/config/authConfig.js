export const authConfig= {
    clientId: import.meta.env.VITE_CLIENT_ID,
    authorizationEndpoint: import.meta.env.VITE_AUTHORIZATION_ENDPOINT,
    tokenEndpoint: import.meta.env.VITE_TOKEN_ENDPOINT,
    redirectUri: import.meta.env.VITE_REDIRECT_URI,
    scope: import.meta.env.VITE_SCOPE,
    // onRefreshTokenExpire: (event) => event.logIn(),
}