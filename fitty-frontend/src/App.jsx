import './App.css'
import {BrowserRouter as Router, Route, Routes, Navigate} from "react-router";
import {Box, Button} from "@mui/material";
import {useContext, useEffect} from "react";
import {AuthContext} from "react-oauth2-code-pkce";
import {useDispatch} from "react-redux";
import {setCredentials} from "./store/authSlice.js";
import Home from "./components/Home.jsx";
import ActivityForm from "./components/ActivityForm.jsx";
import ActivityList from "./components/ActivityList.jsx";
import ActivityDetails from "./components/ActivityDetails.jsx";

const ActivityPage = () => {
    //console.log('ActivityPage rendered');
    return (
        <>
            <Box component="section" sx={{ p: 2, border: '1px dashed grey', backgroundColor: 'white', color: 'black' }}>
                <ActivityForm onActivityAdded={()=> window.location.reload()}/>
                <ActivityList/>
            </Box>
        </>
    )
}

function App() {
    const {token, tokenData,logIn,logOut, isAuthenticated} = useContext(AuthContext)
    //console.log('App rendered, token:', !!token, 'isAuthenticated:', isAuthenticated);
    const dispatch = useDispatch();

    useEffect(() => {
        if(token){
            dispatch(setCredentials({
                token: token,
                user: tokenData,
                userId: tokenData.sub
            }));
        }
    }, [token,tokenData,dispatch]);

  return (
    <>
        <Router>
            { (!token) ?
                (
                    <>
                        <div className="login-container">
                            <Button variant='contained' color='success' onClick={()=> {logIn()} }>Log In</Button>
                            <Home/>
                        </div>
                    </>
                ):(
                    <>
                        <div className="logout-container">
                            <Button variant='text' color='success' onClick={()=>{logOut()}}>Log Out</Button>
                        </div>
                        <Box component="section" sx={{ p: 2, border: '1px dashed grey', backgroundColor: 'white', color: 'black' }}>
                            <Routes>
                                <Route path="/activities" element={<ActivityPage/>} />
                                <Route path="/activities/:id" element={<ActivityDetails/>} />
                                <Route path="/" element={(token) ? <Navigate to="/activities" replace/> : <Home/> } />
                            </Routes>
                        </Box>
                    </>
                )
            }
        </Router>
    </>
  )
}

export default App
