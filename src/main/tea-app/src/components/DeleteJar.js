import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import MenuItem from '@mui/material/MenuItem';

export default function DeleteJar(props) {
  const [open, setOpen] = React.useState(false);
  const [jar, setJar] = React.useState(props.jar);

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

  const handleClose = () => {
    setOpen(false);
  };

  const handleConfirm = async () => {

  const requestOptions = {
      method: 'PUT',
      body: JSON.stringify(jar)
  };

  fetch('http://localhost:9080/api/Jar/' + jar.jarAdmin + '/remove/' + jar.jar_id, requestOptions);

    setOpen(false);

    await delay(1000);
      window.location.reload();

  };


  return (

    <div>
    <MenuItem onClick={handleClickOpen}> Delete Jar </MenuItem>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          {"Delete Jar?"}
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            Are you sure you would like to delete this jar? This action can not be undone.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}> Cancel </Button>
          <Button onClick={handleConfirm} autoFocus> Confirm </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
