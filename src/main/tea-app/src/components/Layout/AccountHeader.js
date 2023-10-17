import React from 'react';
import Box from '@mui/material/Box';
import CssBaseline from '@mui/material/CssBaseline';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import { makeStyles } from '@mui/styles';
import Badge from '@mui/material/Badge';
import MenuItem from '@mui/material/MenuItem';
import Menu from '@mui/material/Menu';
import NotificationsIcon from '@mui/icons-material/Notifications';
import SettingsIcon from '@mui/icons-material/Settings';
import MoreIcon from '@mui/icons-material/MoreVert';
import HelpOutlineIcon from '@mui/icons-material/HelpOutline';
import IconButton from '@mui/material/IconButton';

const useStyles = makeStyles(theme => ({
  accountHeaderDesign: {
    boxShadow: "none",
    backgroundColor: "gray",
  },
  accountHeaderText: {
    color: "black",
    flexGrow: '1',
    textAlign: "center",
    paddingLeft: "325px"
  },
}));

export default function AccountHeader() {
  const [anchorEl, setAnchorEl] = React.useState(null);
  const [mobileMoreAnchorEl, setMobileMoreAnchorEl] = React.useState(null);
  const isMenuOpen = Boolean(anchorEl);
  const isMobileMenuOpen = Boolean(mobileMoreAnchorEl);
  const handleMobileMenuClose = () => {
    setMobileMoreAnchorEl(null);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
    handleMobileMenuClose();
  };

  const handleMobileMenuOpen = (event) => {
    setMobileMoreAnchorEl(event.currentTarget);
  };
  const menuId = 'primary-search-account-menu';

  const classes = useStyles();

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
          <IconButton
            size="large"
            aria-label="show notifications icon"
            color="inherit"
          >
            <Badge>
              <NotificationsIcon />
            </Badge>
          </IconButton>
          <p>Notifications</p>
        </MenuItem>
        <MenuItem>
          <IconButton size="large" aria-label="show Settings icon" color="inherit" >
            <Badge>
              <SettingsIcon />
            </Badge>
          </IconButton>
          <p>Settings</p>
        </MenuItem>
        <MenuItem>
          <IconButton
            size="large"
            aria-label="show Help icon"
            color="inherit"
          >
            <Badge>
              <HelpOutlineIcon />
            </Badge>
          </IconButton>
          <p>Help</p>
        </MenuItem>
      </Menu>
    );

  return (
    <Box sx={{ display: 'flex' }}>
      <CssBaseline />
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
            All Jars
          </Typography>

          <Box sx={{ display: { xs: 'none', md: 'flex' } }} >
            <IconButton
            size="large" color="inherit">
              <Badge>
                <NotificationsIcon />
              </Badge>
            </IconButton>
            <IconButton
              size="large"
              color="inherit"
            >
              <Badge>
                <SettingsIcon />
              </Badge>
            </IconButton>
            <IconButton
            size="large"
            color="inherit">
              <Badge>
                <HelpOutlineIcon />
              </Badge>
            </IconButton>
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
    </Box>
  );
}