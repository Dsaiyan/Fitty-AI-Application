import './App.css';
import {BrowserRouter as Router, Navigate, Route, Routes } from "react-router";
import {Button} from "@mui/material";

function App() {
  return (
    <>
        <Router>
            {/* Your routes and components go here */}
            <Button variant="contained" color="inherit" >LogIn</Button>
        </Router>
    </>
  )
}

export default App
