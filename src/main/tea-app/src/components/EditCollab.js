import React, { useEffect, useState } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import MenuItem from '@mui/material/MenuItem';
import TextField from '@mui/material/TextField';
import { useTheme } from '@mui/material/styles';
import OutlinedInput from '@mui/material/OutlinedInput';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import AddIcon from '@mui/icons-material/Add';
import PersonRemoveIcon from '@mui/icons-material/PersonRemove';
import Alert from '@mui/material/Alert';




export default function EditCollab(props) {

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
      width: 250,
    },
  },
};

const [open, setOpen] = React.useState(false);

  const [anchorEl, setAnchorEl] = React.useState(null);
  const [jar, setJar] = React.useState(props.jar);
  const [addCollabName, setAddCollabName] = React.useState("");
  const [errorBool, setErrorBool] = React.useState(false);

  const delay = ms => new Promise(res => setTimeout(res, ms));

  const open2 = Boolean(anchorEl);
  const handleMenuClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleMenuClose = () => {
    setAnchorEl(null);
  };
  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = async () => {
    setOpen(false);
    await delay(1000);
      window.location.reload();
  };


  const emails = jar.collaborators;
  const [personName, setPersonName] = React.useState("");

    const adjustArray = () => {
    let newArray = new Array();
    for ( let i = 1; i < jar.collaborators.length; i++) {
    newArray.push(jar.collaborators[i]);
    }
    return newArray;
    }



    const handleAdd = async () => {

      let aStatus = false;

      const response = await fetch('http://localhost:9080/api/User/' + addCollabName, {
          method: "GET"
      });
      //const jsonData = await response.json();
      console.log(response.statusText.toString());
      if(response.statusText.toString() == "Found") {
        aStatus = true;
      } else {
        aStatus = false;
      }

      // await getUserJSON(addCollabName);

      await delay(1000);
      

      if(aStatus) {

       let addArray = [];
       for(let i = 0; i < jar.collaborators.length; i++) {
         addArray.push(jar.collaborators[i])
       }
       addArray.push(addCollabName);

       //console.log(addArray);

       const updatedJar = {
        jar_id: jar.jar_id,
        name: jar.name,
        numberOfNotes: jar.numberOfNotes,
        closed: jar.closed,
        jarAdmin: jar.jarAdmin,
        collaborators: addArray,
        openingTime: jar.openingTime
    };


        //console.log(jar);
            const requestOptions = {
            method: 'PUT',
            body: JSON.stringify(updatedJar)
        };
        fetch('http://localhost:9080/api/Jar/' + jar.jarAdmin + '/add/', requestOptions);
      
        const requestOptions2 = {
          method: 'POST',
          body: JSON.stringify(updatedJar)
      };
      fetch('http://localhost:9080/api/Jar/' + addCollabName, requestOptions2);
      setErrorBool(false);
      handleClose();
    } else {
      setErrorBool(true);
    }

    
    }

    const handleDelete = () => {
      const requestOptions = {
        method: 'PUT',
        body: JSON.stringify(jar)
    };
    fetch('http://localhost:9080/api/Jar/' + personName + '/remove/', requestOptions);
    handleClose();

    }


return (
<div>
<MenuItem onClick={handleClickOpen}> Edit Collaborators </MenuItem>
<Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
        sx={{
              "& .MuiDialog-container": {
                "& .MuiPaper-root": {
                  width: "100%",
                  maxWidth: "500px",  // Set your width here
                },
              },
            }}
      >
      <DialogTitle>Edit Collaborators</DialogTitle>
                      <DialogContent>
                          <DialogContentText>
                          Add or Remove Collaborators
                          </DialogContentText>
                      <TextField
                                  autoFocus
                                  margin="dense"
                                  id="name"
                                  label="Email Address"
                                  type="email"
                                  fullWidth
                                  variant="standard"
                                  value={addCollabName}
                                  onInput={(event) => {
                                    const v = event.target.value;
                                    setAddCollabName(v)
                                  }}
                                />
                        
                        { errorBool ? <Alert variant="filled" severity="error">
                                      Invalid user. Please try again.
                                      </Alert> : null
                                  }
                      </DialogContent>
      <DialogActions>
      { addCollabName !== "" ?  <Button variant="contained" endIcon={<AddIcon />}
                                        onClick={handleAdd}>
                             Add
                           </Button> : null }
      </DialogActions>


      <FormControl sx={{ m: 1, width: 470 }}>
              <InputLabel id="demo-multiple-name-label">Name</InputLabel>
              <Select
                labelId="demo-multiple-name-label"
                id="demo-multiple-name"
                value={personName}
                onChange={(event) => {
                  const v = event.target.value;
                  setPersonName(v)
                }}
                input={<OutlinedInput label="Email" />}
                MenuProps={MenuProps}
              >
                {adjustArray().map((name) => (
                  <MenuItem
                    key={name}
                    value={name}
                  >
                    {name}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
            <DialogActions>

              {
                personName !== "" ?
                <Button variant="contained" endIcon={<PersonRemoveIcon />}
                onClick={handleDelete}>
                    Remove
                  </Button> : null
              }
            

      </DialogActions>
            </Dialog>
</div>
)
}