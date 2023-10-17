import React from 'react';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Tooltip from '@mui/material/Tooltip';
import { IconButton } from '@mui/material';
import EmojiFoodBeverageIcon from '@mui/icons-material/EmojiFoodBeverage';
import MenuItem from '@mui/material/MenuItem';


export default function EditOpeningTime(props) {

const addDays = (days) => {
        let currentDate = new Date();
        currentDate.setDate(currentDate.getDate() + days);

        return currentDate.toISOString().slice(0,10);
    }
const [jar, setJar] = React.useState(props.jar);
const [open, setOpen] = React.useState(false);
    //const [jarName, setJarName] = React.useState("");
    const [jarOpeningTime, setJarOpeningTime] = React.useState("");

  const [anchorEl, setAnchorEl] = React.useState(null);
  const open2 = Boolean(anchorEl);

  const delay = ms => new Promise(res => setTimeout(res, ms));
  const handleMenuClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleMenuClose = () => {
    setAnchorEl(null);
  };
  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleCreate = async () => {

    const updatedJar = {
          jar_id: jar.jar_id,
          name: jar.name,
          numberOfNotes: jar.numberOfNotes,
          closed: jar.closed,
          jarAdmin: jar.jarAdmin,
          collaborators: jar.collaborators,
          openingTime: jarOpeningTime
    }

    const requestOptions = {
      method: 'PUT',
      body: JSON.stringify(updatedJar)
  };

  fetch('http://localhost:9080/api/Jar/' + jar.jarAdmin + '/add/', requestOptions);


  setOpen(false);
  await delay(1000);
      window.location.reload();

};

  const handleClose = () => {
    setOpen(false);
  };
  return(
  <div>
  <MenuItem onClick={handleClickOpen}> Edit Jar Opening Time </MenuItem>
  <Dialog
  open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
      <DialogTitle>Edit Jar Opening Time</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                    Select New Jar Opening Time
                    </DialogContentText>
                    <TextField
                    required
                    id="date"
                    helperText="Select jar opening time."
                    type="date"
                    defaultValue="yyyy-mm-dd"
                    sx={{ width: 220 }}
                    InputProps={{inputProps: { min: addDays(1), max: addDays(365) }}}
                    InputLabelProps={{
                    shrink: true,
                    }}
                    value={jarOpeningTime}
                    onInput={(event) => {
                    setJarOpeningTime(event.target.value)
                    }}
                    />
                    </DialogContent>
                    <DialogActions>
                    <Button onClick={handleClose}> Cancel </Button>
                    {jarOpeningTime !== "" ?
                    <Button onClick={handleCreate} autoFocus> Update </Button>
                    : null

                }
                    </DialogActions>
                    </Dialog>
                    </div>
                    )
                    }