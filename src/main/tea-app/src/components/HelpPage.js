import React from 'react';
import Dialog from '@mui/material/Dialog';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import CloseIcon from '@mui/icons-material/Close';
import Slide from '@mui/material/Slide';
import Tooltip from '@mui/material/Tooltip';
import HelpOutlineIcon from '@mui/icons-material/HelpOutline';
import { makeStyles } from '@mui/styles';
import Accordion from '@mui/material/Accordion';
import AccordionDetails from '@mui/material/AccordionDetails';
import AccordionSummary from '@mui/material/AccordionSummary';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import CardMedia from '@mui/material/CardMedia';
import createJarImage from './../images/createjarimage.png';
import deleteJarImage from './../images/deletejar.png';
import addNote from './../images/addnote.png';
import editCollab from './../images/editcollab.png';
import deleteNote from './../images/deletenote.png';

const useStyles = makeStyles(theme => ({
  helpHeaderDesign: {
    boxShadow: "none",
    backgroundColor: "#e5fdd3",

    textAlign: "center",
  },
  helpHeaderText: {
    color: "#3c3c0c",
  }}));

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

export default function FullScreenDialog() {
  const [expanded, setExpanded] = React.useState(false);

  const handleChange = (panel) => (event, isExpanded) => {
    setExpanded(isExpanded ? panel : false);
  };

  const classes = useStyles();
  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <div>
      <Tooltip title="Help Page"><IconButton aria-label="help page"
        onClick={handleClickOpen}>
           <HelpOutlineIcon />
      </IconButton></Tooltip>
      <Dialog
        fullScreen
        open={open}
        onClose={handleClose}
        TransitionComponent={Transition}
      >
        <AppBar
        className={classes.helpHeaderDesign}
        sx={{ position: 'relative' }}>
          <Toolbar>
            <IconButton
              edge="start"
              color="inherit"
              onClick={handleClose}
              aria-label="close"
              className={classes.helpHeaderText}
            >
              <CloseIcon />
            </IconButton>
            <Typography
            className={classes.helpHeaderText}
            sx={{ ml: 2, flex: 1 }} variant="h6" component="div">
              What can we help you with?
            </Typography>
          </Toolbar>
        </AppBar>
        <div>
            <Accordion expanded={expanded === 'panel1'} onChange={handleChange('panel1')}>
                <AccordionSummary
                  expandIcon={<ExpandMoreIcon />}
                  aria-controls="panel1bh-content"
                  id="panel1bh-header"
                >
                  <Typography sx={{ width: '33%', flexShrink: 0 }}>
                    Tutorials
                  </Typography>
                </AccordionSummary>
                <AccordionDetails>
                  <Typography>
                    Create a jar
                    <CardMedia
                        component="img"
                        src={createJarImage}
                        sx={{ width: 200 }}
                        alt="Create a jar image"
                    />
                    <div />
                    1: On the second to top right corner, click on the create jar button.
                    <div />
                    2: Create a name for your jar.
                    <div />
                    3: Select a date you want the jar to be opened for use.
                    <div />
                    4: After setting a specific date, your jar will appear, ready for notes.
                    <div />
                    Create a note
                    <div />
                    <CardMedia
                        component="img"
                        src={addNote}
                        sx={{ width: 250 }}
                        alt="Add a note image"
                    />
                    <div />
                    1: Click on the create note button.
                    <div />
                    2: Write down the name of the note.
                    <div />
                    3: Write down something positive that happened to you recently.
                    <div />
                    4: Give your note a rating and then click save.
                    <div />
                    Remove a jar
                    <div />
                    <CardMedia
                        component="img"
                        src={deleteJarImage}
                        sx={{ width: 280 }}
                        alt="Delete a jar image"
                    />
                    <div />
                    1: Click on setting button of your jar.
                    <div />
                    2: Click on the delete jar button.
                    <div />
                    3: Confirm you want to remove the jar.
                    <div />
                    Remove a note
                    <div />
                    <CardMedia
                        component="img"
                        src={deleteNote}
                        sx={{ width: 250 }}
                        alt="Remove a note image" />
                    <div />
                    1: Click on the view notes log.
                    <div />
                    2: Select the note you want to remove.
                    <div />
                    3: Click on the delete icon on the top right corner to delete the note.
                    <div />
                    Add/Remove a Collaborator
                    <div />
                    <CardMedia
                        component="img"
                        src={editCollab}
                        sx={{ width: 280 }}
                        alt="Edit a collaborator image"
                    />
                    <div />
                    1: Click on setting button of your jar.
                    <div />
                    2: Click on the edit collaborator button.
                    <div />
                    3: To add a user: Enter the user's email then click add.
                    <div />
                    4: To remove a user: Select their name in the name section and remove them.
                    <div />
                  </Typography>
                </AccordionDetails>
              </Accordion>
              <Accordion expanded={expanded === 'panel2'} onChange={handleChange('panel2')}>
                <AccordionSummary
                  expandIcon={<ExpandMoreIcon />}
                  aria-controls="panel2bh-content"
                  id="panel2bh-header"
                >
                  <Typography sx={{ width: '33%', flexShrink: 0 }}>Guides</Typography>
                </AccordionSummary>
                <AccordionDetails>
                  <Typography>
                    1. All Jars
                    <div />
                    All Jars displays all the jars the user ever created.
                    <div />
                    2. Active Jars
                    <div />
                    Active Jars displays the jars that are active to the user,
                    <div />
                    the ones they still write notes in.
                    <div />
                    3. Archived Jars
                    <div />
                    Archived Jar displays the jars that are filled and stored away,
                    <div />
                    only to review past notes.
                    <div />
                    4. View Notes Log
                    <div />
                    View Notes Log displays the notes in the jar,
                    <div />
                    and it can be filtered anyhow.
                    <div />
                    5. Jar stats
                    <div />
                    Jar stats displays the date the jar was opened, the total notes in the jar,
                    <div />
                    and how many collaborators has access to the jar.
                    <div />
                    6. Jar setting
                    <div />
                    Jar settings displays buttons were a user can change
                    <div />
                    its opening date, delete the jar, or add/remove
                    <div />
                    the collaborators involved in the jar.
                    <div />

                  </Typography>
                </AccordionDetails>
              </Accordion>
              <Accordion expanded={expanded === 'panel4'} onChange={handleChange('panel4')}>
                <AccordionSummary
                  expandIcon={<ExpandMoreIcon />}
                  aria-controls="panel4bh-content"
                  id="panel4bh-header"
                >
                  <Typography sx={{ width: '33%', flexShrink: 0 }}>Privacy and Safety</Typography>
                </AccordionSummary>
                <AccordionDetails>
                  <Typography>
                    Positivitea is committed to securing your data and keeping it confidential.
                    <div />
                    Positivitea has done all in its power to prevent data theft,
                    <div />
                    unauthorized access, and disclosure by implementing the latest technologies and software,
                    <div />
                    which help us safeguard all the information we collect online.
                  </Typography>
                </AccordionDetails>
              </Accordion>
            </div>
      </Dialog>
    </div>
  );
}