import * as React from 'react';
import Button from '@mui/material/Button';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Fade from '@mui/material/Fade';
import { IconButton, Typography } from '@mui/material';
import DeleteJar from "./DeleteJar";
import EditOpeningTime from "./EditOpeningTime";
import SettingsOutlinedIcon from '@mui/icons-material/SettingsOutlined';
import EditCollab from "./EditCollab";

export default function JarSetting(props) {
  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };
  const [jar, setJar] = React.useState(props.jar);

  return (
    <>
        <IconButton aria-label="jar settings"
        aria-controls={open ? 'fade-menu' : undefined}
        aria-haspopup="true"
        aria-expanded={open ? 'true' : undefined}
        onClick={handleClick}>
      <SettingsOutlinedIcon />
  </IconButton>
      <Menu
        id="fade-menu"
        MenuListProps={{
          'aria-labelledby': 'fade-button',
        }}
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
        TransitionComponent={Fade}
      >
        <EditOpeningTime 
        jar={jar}/>
        <EditCollab
        jar={jar}/>
        <DeleteJar 
        jar={jar}/>
      </Menu>
    </>
  );
}
