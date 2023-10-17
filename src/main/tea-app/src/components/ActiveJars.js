import React, { useState, useEffect } from "react";
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import { IconButton, Typography } from '@mui/material';
import EmptyJar from '../images/emptyjar.png';
import ClosedJar from '../images/half_full_with_lid.png';
import OpenedJar from '../images/full_with_no_lid.png';
import ContentPasteIcon from '@mui/icons-material/ContentPaste';
import InfoOutlinedIcon from '@mui/icons-material/InfoOutlined';
import FileDownloadOutlinedIcon from '@mui/icons-material/FileDownloadOutlined';
import Tooltip from '@mui/material/Tooltip';
import SettingsOutlinedIcon from '@mui/icons-material/SettingsOutlined';
import CreateNote from './CreateNote';
import NotesLog from './NotesLog';
import JarStats from './JarStats';
import JarSetting from './JarSetting';
import JarOpeningCeremony from "./JarOpeningCeremony";

function ActiveJars() {
  //Stores state of userJSON returned from the GET Request
  const [userData, setUserData] = useState({});
  useEffect(() => {
      getUserJSON();
  },[]);


  const userID = localStorage.getItem("token_id"); //dummy placeholder variable that will be replaced once login is added

  //GET Method that obtains the User Document from the server
  const getUserJSON = async () => {
      const response = await fetch('http://localhost:9080/api/User/' + userID, {
          method: "GET"
      });
      const jsonData = await response.json();
      console.log(jsonData);
      setUserData(jsonData);
  };

  localStorage.setItem("user",userData);
  const UserTest = userData; //dummy variable for testing my local json, will be set to userData later
  //accesses myJars array
  const myJars = UserTest.my_Jars;

  //styles jar components
  const Item = styled(Paper)(({ theme }) => ({
      ...theme.typography.body2,
      padding: theme.spacing(1),
      textAlign: 'center',
      color: theme.palette.text.secondary,
    }));

    //Method to determine what jar image should be displayed based on notes # and closed status
    const getJarImage = (num, jarStatus) => {
        var imgSource;
        if(num === 0 || num === undefined) {
          imgSource = EmptyJar;
        } else if(num > 0 && jarStatus) {
          imgSource = ClosedJar;
        } else {
            imgSource = OpenedJar;
        }

        return imgSource;

    }

    const getCurrentDate = () => {
        const currentDate = new Date();

        return currentDate;
    }

    //console.log(UserTest.my_Jars);

    //Checks if the users has any jars
    if(UserTest.my_Jars !== undefined) {
        //If there are jars, populate the page with jar components
        return(
          <Grid
                     paddingLeft={40}
                     paddingTop={10}
                     container spacing={10}
                     display="flex"
                     justify="center">
          {myJars.filter(jar => jar.closed).map( filteredJar => {
              return(
                 <span key={filteredJar.jar_id}>
                         <Grid item xs={12} container justify="center" alignItems="center">
          <Item>
                  <Box
                      sx={{
                      height: 250,
                      width: 220.5,    
                      }}
                  >
                  <Box>
                      <Typography>{filteredJar.name}</Typography>
                  </Box>
                  <img src={getJarImage(filteredJar.numberOfNotes, filteredJar.closed)} height= "173" width= "211.5" alt="empty jar" />
               
  
                  {filteredJar.closed && getCurrentDate()  < new Date(filteredJar.openingTime) ? 
  
                  
                    <CreateNote 
                    jarId={filteredJar.jar_id} 
                    email={UserTest.email}/>
                      :
                  
                      null
  
                  }



                    {
                        filteredJar.closed && UserTest.email === filteredJar.jarAdmin && new Date(filteredJar.openingTime) <= getCurrentDate() ?
                        <JarOpeningCeremony 
                        jar={filteredJar}
                        userId={UserTest.token_id}/> : null
                    }
                  
  
                  <Tooltip title="View Notes Log"><IconButton aria-label="notes log">
                        <NotesLog 
                        userId={UserTest.token_id}
                        jar={filteredJar}
                        />
                    </IconButton></Tooltip>
  
                  <JarStats jarId={filteredJar.jar_id}
                              userId={UserTest.token_id}
                              myJars={UserTest.my_Jars}/>
  
                  {UserTest.email === filteredJar.jarAdmin ? 
                  
                      <JarSetting
                      jar={filteredJar}
                      />: null
              
              }
  
                  
  
                  </Box>
                  </Item>
                  </Grid>

  
                 </span>
              )
          })} 
          

          </Grid>
        )

    } 
    //If there are no jars, populate the page with the create jar element
    else {
        return(
          null
        )
    }

}

export default ActiveJars;