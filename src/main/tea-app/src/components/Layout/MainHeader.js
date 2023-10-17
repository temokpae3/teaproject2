import React from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import { makeStyles } from '@mui/styles';
import CardMedia from '@mui/material/CardMedia';
import logo from './logo.png';

const drawerWidth = 240;

const useStyles = makeStyles(theme => ({
  headerDesign: {
    boxShadow: "none",
    backgroundColor: "#e5fdd3",
    alignItems: 'center',
    flexGrow: '1',
    borderColor: "#aabe9c",
  },
  headerText: {
    color: "#3c3c0c",
  },
}));

export default function MainHeader() {
const classes = useStyles();

    return (
      <AppBar
        position="fixed"
        className = {classes.headerDesign}
      >
        <Toolbar>
          <Typography
          className={classes.headerText}
          variant="h2"
          noWrap component="div">
            Positivitea
          </Typography>
          <CardMedia
            component="img"
            src={logo}
            sx={{ width: 80 }}
            alt="Positivitea Logo"/>
        </Toolbar>
      </AppBar>
  );
 }