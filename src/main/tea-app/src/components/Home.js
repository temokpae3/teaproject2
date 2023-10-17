import React from 'react';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import CssBaseline from '@mui/material/CssBaseline';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import ListItemText from '@mui/material/ListItemText';
import { makeStyles } from '@mui/styles';
import ListItemButton from '@mui/material/ListItemButton';
import Badge from '@mui/material/Badge';
import MenuItem from '@mui/material/MenuItem';
import Menu from '@mui/material/Menu';
import MoreIcon from '@mui/icons-material/MoreVert';
import IconButton from '@mui/material/IconButton';
import MainHeader from './Layout/MainHeader';
import CreateJar from './CreateJar';
import HelpPage from './HelpPage';
import LogoutIcon from '@mui/icons-material/Logout';
import Tooltip from '@mui/material/Tooltip';

import {
  NavLink
  } from 'react-router-dom';
  import { Navigate } from 'react-router-dom';

const drawerWidth = 240;

const useStyles = makeStyles(theme => ({
  headerDesign: {
    boxShadow: "none",
    backgroundColor: "#e5fdd3",
    alignItems: 'center',
    flexGrow: '1',
    borderColor: "#aabe9c",
    textAlign: "center",
  },
  headerText: {
    color: "#3c3c0c",
  },
  accountHeaderDesign: {
      boxShadow: "none",
      backgroundColor: "#e5c29fff",
  },
  accountHeaderText: {
      color: "black",
      flexGrow: '1',
      textAlign: "center",
  },
  menuDesign: {
    backgroundColor: "#434A42"
  },
 menuText: {
    color: "white"
 },
 menuSelecting: {
     '&$selected': {
       backgroundColor: '#353B34',
       '&:hover': {
         backgroundColor: '#3C423B',
       },
     },
   },
   selected: {},
 
}));

export default function Home() {
  const[webpageTitle, setWebpageTitle] = React.useState('All Jars');
  const[logoutSuccess, setLogoutSuccess] = React.useState(false);

  const [anchorEl, setAnchorEl] = React.useState(null);
  const [mobileMoreAnchorEl, setMobileMoreAnchorEl] = React.useState(null);
  const [loginData, setLoginData] = React.useState({});
  const isMenuOpen = Boolean(anchorEl);
  const isMobileMenuOpen = Boolean(mobileMoreAnchorEl);
  const handleMobileMenuClose = () => {
    setMobileMoreAnchorEl(null);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
    handleMobileMenuClose();
  };

  const handleLogout = () => {
    localStorage.removeItem('loginData');
    setLoginData(null);
    setLogoutSuccess(true);
  };

  const handleMobileMenuOpen = (event) => {
    setMobileMoreAnchorEl(event.currentTarget);
  };
  const menuId = 'primary-search-account-menu';

  const classes = useStyles();

  const [selectedIndex, setSelectedIndex] = React.useState(1);

    const handleListItemClick = (event, index) => {
      setSelectedIndex(index);
      if(index === 1) {
        setWebpageTitle('All Jars');
      } else if (index === 2) {
        setWebpageTitle('Active Jars');
      } else if (index === 3) {
        setWebpageTitle('Archived Jars');
      } else {
        setWebpageTitle('About Positivitea');
      }
    };

    const renderMenu = (
      <Menu
        anchorEl={anchorEl}
        anchorOrigin={{
          vertical: 'top',
          horizontal: 'right',
        }}
        id={menuId}
        keepMounted
        transformOrigin={{
          vertical: 'top',
          horizontal: 'right',
        }}
        open={isMenuOpen}
        onClose={handleMenuClose}
      >
      </Menu>
    );

    const mobileMenuId = 'primary-search-account-menu-mobile';

    const renderMobileMenu = (
      <Menu
        anchorEl={mobileMoreAnchorEl}
        anchorOrigin={{
          vertical: 'top',
          horizontal: 'right',
        }}
        id={mobileMenuId}
        keepMounted
        transformOrigin={{
          vertical: 'top',
          horizontal: 'right',
        }}
        open={isMobileMenuOpen}
        onClose={handleMobileMenuClose}
      >

        <MenuItem>
        <CreateJar />
        <p>Create</p>
        </MenuItem>
        <MenuItem>
          <HelpPage />
          <p>Help</p>
        </MenuItem>
        <MenuItem>
            <IconButton onClick = {handleLogout}>
                <LogoutIcon />
            </IconButton>
            <p>Logout</p>
        </MenuItem>
      </Menu>
    );

  return (
    <Box sx={{ display: 'flex' }}>
      <CssBaseline />
      <MainHeader />
      <Drawer
      classes={{ paper: classes.menuDesign }}
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          '& .MuiDrawer-paper': {
            width: drawerWidth,
            boxSizing: 'border-box',
          },
        }}
        variant="permanent"
        anchor="left"
      >
        <Toolbar />
        <Divider />
        <List 
          className={classes.menuText}
          component="nav" aria-label="main mailbox folders">
                <ListItemButton
                  selected={selectedIndex === 1}
                  onClick={(event) => handleListItemClick(event, 1)}
                  component = {NavLink} to="/all-jars"
                  classes={{ root: classes.menuSelecting, selected: classes.selected }}
                  sx={{
                    "&:hover": {
                        color: "white"
                    }
                  }}
                >
                  <ListItemText primary ="All Jars" />
                </ListItemButton>
                <ListItemButton
                  selected={selectedIndex === 2}
                  onClick={(event) => handleListItemClick(event, 2)}
                  component = {NavLink} to="/active-jars"
                  classes={{ root: classes.menuSelecting, selected: classes.selected }}
                  sx={{
                    "&:hover": {
                        color: "white"
                    }
                  }}
                >
                  <ListItemText primary="Active Jars" />
                </ListItemButton>
                <ListItemButton
                  selected={selectedIndex === 3}
                  onClick={(event) => handleListItemClick(event, 3)}
                  component = {NavLink} to="/archived-jars"
                  classes={{ root: classes.menuSelecting, selected: classes.selected }}
                  sx={{
                    "&:hover": {
                        color: "white"
                    }
                  }}
                >
                  <ListItemText primary="Archived Jars" />
                </ListItemButton>
                <ListItemButton
                  selected={selectedIndex === 4}
                  onClick={(event) => handleListItemClick(event, 4)}
                  component = {NavLink} to="/about"
                  classes={{ root: classes.menuSelecting, selected: classes.selected }}
                  sx={{
                      "&:hover": {
                        color: "white"
                      }
                  }}
                >
                  <ListItemText primary="About Positivitea" />
                </ListItemButton>
              </List>
      </Drawer>
      <Box
        component="main"
        sx={{ flexGrow: 1 }}>
        <Toolbar />
        <AppBar
        className={classes.accountHeaderDesign}
        position="static">
        <Toolbar>
          <Typography
            variant="h5"
            noWrap
            component="div"
            className={classes.accountHeaderText}
            sx={{ display: { xs: 'block', sm: 'block' } }}
          >
            {webpageTitle}
          </Typography>

          <Box sx={{ display: { xs: 'none', md: 'flex'} } }>
            <CreateJar />
          </Box>
            <Box sx={{ display: { xs: 'none', md: 'flex'} }}>
              <HelpPage />
            </Box>
            <Box sx={{ display: {xs: 'none', md: 'flex' } }}>
            <Tooltip title="Logout">
            <IconButton onClick = {handleLogout}>
             <LogoutIcon />
            </IconButton>
            </Tooltip>
            </Box>
          <Box sx={{ display: { xs: 'flex', md: 'none' } }}>
            <IconButton
              size="large"
              aria-label="show more"
              aria-controls={mobileMenuId}
              aria-haspopup="true"
              onClick={handleMobileMenuOpen}
              color="inherit"
            >
              <MoreIcon />
            </IconButton>
          </Box>
        </Toolbar>
      </AppBar>
      {renderMobileMenu}
      {renderMenu}
      </Box>
      {logoutSuccess ?
      <Navigate to="/" /> : null
    }
    </Box>
    
  );
}
