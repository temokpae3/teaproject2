import React from 'react';
import './App.css';

import { BrowserRouter as Router, Route, Routes, useLocation } from "react-router-dom";
import { StyledEngineProvider } from "@mui/material/styles";
import {
  ErrorBoundary,
  HomeLayout,
  Login,
  AllJars,
  ActiveJars,
  ArchivedJars,
  About
} from "./components";

function App() {

       return (
        <StyledEngineProvider injectFirst>

          <Router>
          
           <Routes>
             <Route element={<HomeLayout />}>             
              <Route path="/all-jars" element={ <AllJars />} />
              <Route path="/active-jars" element={ <ActiveJars />} />
              <Route path="/archived-jars" element={ <ArchivedJars />} />
              <Route path="/about" element={ <About />} />
              </Route>
              <Route path="/" element={ <Login/>} />
            </Routes>

            </Router>


  
  </StyledEngineProvider>
        );
    };

export default App;