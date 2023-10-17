import React, {useEffect} from 'react';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import Box from '@mui/material/Box';
import ListItemText from '@mui/material/ListItemText';
import ListItem from '@mui/material/ListItem';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import CloseIcon from '@mui/icons-material/Close';
import Slide from '@mui/material/Slide';
import EmojiFoodBeverageIcon from '@mui/icons-material/EmojiFoodBeverage';
import Curtains from './../images/curtain_final.gif';
import Brain from './../images/burp_final_fixed.gif';
import { ClassNames } from '@emotion/react';
import { makeStyles } from '@mui/styles';
import CoffeeIcon from '@mui/icons-material/Coffee';
import Tooltip from '@mui/material/Tooltip';


const useStyles = makeStyles(theme => ({
    curtains: {
        backgroundPosition: 'center',
        backgroundSize: 'cover',
        backgroundRepeat: 'no-repeat',
        width: '99vw',
        height: '95vh'
    },
    brain: {
        width: '20vw',
        height: '50vh',
        padding: '10px'
    },
    dialog: {
        backgroundColor: '#e5fdd3'
    },
    button: {
        display: 'inline',
        width: '100%',
        border: 'none',
        backgroundColor: '#e5c29fff',
        color: "#3c3c0c",
        padding: '28px',
        fontSize: '28px',
        cursor: 'pointer',
        textAlign: 'center'
    }
    
}))

const Transition = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="up" ref={ref} {...props} />;
  });

export default function JarOpeningCeremony(props) {
    const [jar, setJar] = React.useState(props.jar);
    const [userId, setUserId] = React.useState(props.userId);
    const [dialogOpen, setDialogOpen] = React.useState(false);
    const [activeNote, setActiveNote] = React.useState({});
    const [count, setCount] = React.useState(0);
    const [notes, setNotes] = React.useState([]);
    const [curtains, setCurtainsBool] = React.useState(true);
    useEffect(() => {
        getNotesJsonData();
     },[]);

     const getNotesJsonData = async () => {
          const response = await fetch('http://localhost:9080/api/Note/jar_id/' + jar.jar_id, {
               method: "GET"
           });
           const jsonData = await response.json();
           console.log(jsonData);
           for(let i = 0; i < jsonData.length; i++) {
            jsonData[i] = JSON.parse(jsonData[i]);
          }
          setNotes(jsonData); 
          //console.log(notes);  
            
      }
      const classes = useStyles();
      const delay = ms => new Promise(res => setTimeout(res, ms));

    
    const handleClickOpen = async () => {
        setDialogOpen(true); 
        await delay(5400);
        setCurtainsBool(false);
    }

    const handleClickNext = () => {
        console.log(notes);
        setActiveNote(notes[(count + 1) % notes.length]);
        setCount(count+1);
    }

    const handleClickClose = async () => {
        setDialogOpen(false);
        //PUT request to update jar closed status to false (meaning it's open)
        const updatedJar = {
            jar_id: jar.jar_id,
            name: jar.name,
            numberOfNotes: jar.numberOfNotes,
            closed: false,
            jarAdmin: jar.jarAdmin,
            collaborators: jar.collaborators,
            openingTime: jar.openingTime   
        }
  
        const requestOptions = {
            method: 'PUT',
            body: JSON.stringify(updatedJar)
        };
        const r = fetch('http://localhost:9080/api/Jar/' + userId + '/add/', requestOptions);

      await delay(1000);
      window.location.reload();


    }


    return(
        
      <><Tooltip title="Open Jar">
        <IconButton aria-label="open jar" onClick={handleClickOpen}><CoffeeIcon />
        </IconButton></Tooltip><Dialog
            className={classes.dialog}
            fullScreen
            open={dialogOpen}
            onClose={handleClickClose}
            TransitionComponent={Transition}
        >
            <AppBar sx={{ position: 'relative' }}
                    style={{backgroundColor: '#3c3c0c'}}>
                <Toolbar
                backgroundColor="#3c3c0c">
                    <IconButton
                        edge="start"
                        color="inherit"
                        onClick={handleClickClose}
                        aria-label="close"
                    >
                        <CloseIcon />
                    </IconButton>
                    <Typography sx={{ ml: 2, flex: 1 }} variant="h6" component="div" color="white">
                        Jar Opening Ceremony
                    </Typography>
                </Toolbar>
            </AppBar>

            {curtains ? 
                <img src={Curtains} className={classes.curtains} />
                 :
                <><><Box 
        backgroundColor='#e5fdd3'
        display='flex'
        alignItems="center"
                justifyContent="center"
                height="85vh"
            
        sx={{ border: 1 }}
                    TransitionComponent={Transition}>
                       <span><Typography variant="h3" color="#3c3c0c">"{activeNote.message}"</Typography>
                        <Typography variant="h4" color="#3c3c0c">Author: {activeNote.author}</Typography>
                        <Typography variant="h4" color="#3c3c0c">Rating: {activeNote.rating}</Typography>
                        <Typography variant="h4" color="#3c3c0c">Date Posted: {activeNote.time}</Typography></span>
                        <span><img src={Brain} className={classes.brain}/></span>
                    </Box>
                    <Box display="flex" 
                alignItems="center"
                justifyContent="center">
                    <button className={classes.button}
                    onClick={handleClickNext}>Next Note</button>
                </Box></>
                    
                    </>
                }

            
        </Dialog></>
    )

}