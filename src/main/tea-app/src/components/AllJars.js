import React, { useState, useEffect } from "react";
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import { getNativeSelectUtilityClasses, IconButton, Typography } from '@mui/material';
import EmptyJar from '../images/emptyjar.png';
import ClosedJar from '../images/half_full_with_lid.png';
import OpenedJar from '../images/full_with_no_lid.png';
import ContentPasteIcon from '@mui/icons-material/ContentPaste';
import InfoOutlinedIcon from '@mui/icons-material/InfoOutlined';
import FileDownloadOutlinedIcon from '@mui/icons-material/FileDownloadOutlined';
import Tooltip from '@mui/material/Tooltip';
import SettingsOutlinedIcon from '@mui/icons-material/SettingsOutlined';
import CreateNote from './CreateNote';
import JarStats from './JarStats';
import Button from '@mui/material/Button';
import NotesLog from './NotesLog';
import JarSetting from './JarSetting';
import JarOpeningCeremony from "./JarOpeningCeremony";
import { makeStyles } from '@mui/styles';

const useStyles = makeStyles({
  root: {
    "&:hover": {
      borderRadius: "50%",
    }
  }
});

function AllJars() {
    const classes = useStyles();
   //Stores state of userJSON returned from the GET Request
    const [userData, setUserData] = useState({});
    useEffect(() => {
       getUserJSON();
    },[]);


    const userID = localStorage.getItem("token_id");
    //console.log(localStorage.getItem("token_id"));

   //GET Method that obtains the User Document from the server
    const getUserJSON = async () => {
       const response = await fetch('http://localhost:9080/api/User/' + userID, {
           method: "GET"
       });
       const jsonData = await response.json();
       //console.log(jsonData);
       setUserData(jsonData);
    };

    
    const UserTest = userData; //dummy variable for testing my local json, UserTestJson, will be set to userData later
    localStorage.setItem("user", JSON.stringify(UserTest));
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
          //console.log("Num: " + num + " Jar Status: " + jarStatus);
          if(num === 0 || num === undefined) {
            imgSource = EmptyJar;
          } else if(num > 0 && jarStatus) {
            imgSource = ClosedJar;
          } else {
              imgSource = OpenedJar;
          }

          return imgSource;

      }

      //function to get the current date for comparison purposes
      const getCurrentDate = () => {
          const currentDate = new Date();

          return currentDate;
      }

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
            {myJars && myJars.map( jar => {
                return(
                   <span key={jar.jar_id}>
                           <Grid item xs={12} container justify="center" alignItems="center">
            <Item>
                    <Box
                        sx={{
                        height: 250,
                        width: 220.5,
                        }}
                    >
                    <Box>
                        <Typography>{jar.name}</Typography>
                    </Box>
                    <img src={getJarImage(jar.numberOfNotes, jar.closed)} height= "173" width= "211.5" alt="empty jar" />
                 
                    {jar.closed && getCurrentDate()  < new Date(jar.openingTime)  ?           
                      <CreateNote 
                        jarId={jar.jar_id} 
                        email={UserTest.email}/>
                        :
                    
                        null

    
                    }

                    
                    {
                        jar.closed && UserTest.email === jar.jarAdmin && new Date(jar.openingTime) <= getCurrentDate() ?
                        <JarOpeningCeremony 
                        jar={jar}
                        userId={UserTest.token_id}/> : null
                    }
                    
    
                    <Tooltip title="View Notes Log"><IconButton className={classes.root} aria-label="notes log">
                        <NotesLog
                        className={classes.root}
                        userId={UserTest.token_id}
                        jar={jar}
                        />
                    </IconButton></Tooltip>

    
                    <JarStats jarId={jar.jar_id}
                              userId={UserTest.token_id}
                              myJars={UserTest.my_Jars}/>
    
                    {UserTest.email === jar.jarAdmin ?

                        <JarSetting
                         jar = {jar}/> : null

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

export default AllJars;
