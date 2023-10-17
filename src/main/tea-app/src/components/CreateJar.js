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
export default function CreateJar() {

    const [openCreateJar, setOpenCreateJar] = React.useState(false);
    const [jarName, setJarName] = React.useState("");
    const [jarOpeningTime, setJarOpeningTime] = React.useState("");

    const delay = ms => new Promise(res => setTimeout(res, ms));
    
    const handleClickCreateJar = () => {
        setOpenCreateJar(true);
    };

    const handleCloseCreateJar = () => {
        setOpenCreateJar(false);
    };

    const handleCreateJar = async () => {
        console.log(jarName);
      
      const jar = {
          jar_id: 1234,
          name: jarName,
          numberOfNotes: 0,
          closed: true,
          jarAdmin: localStorage.getItem("token_id"),
          collaborators: [localStorage.getItem("token_id")],
          openingTime: jarOpeningTime
      }
      console.log(jar);
      const requestOptions = {
          method: 'POST',
          body: JSON.stringify(jar)
      };
      console.log('http://localhost:9080/api/Jar/' + localStorage.getItem("token_id"));
      const r = fetch('http://localhost:9080/api/Jar/' + localStorage.getItem("token_id"), requestOptions);
      
      setOpenCreateJar(false);

      await delay(1000);
      window.location.reload();

    };

    const addDays = (days) => {
        let currentDate = new Date();
        currentDate.setDate(currentDate.getDate() + days);

        return currentDate.toISOString().slice(0,10);
    }


    return(
        <><Tooltip title="Create Jar"><IconButton aria-label="create jar"
            onClick={handleClickCreateJar}>
            <EmojiFoodBeverageIcon />
        </IconButton></Tooltip><Dialog open={openCreateJar} onClose={handleCloseCreateJar}>
                <DialogTitle>Create Jar</DialogTitle>
                <DialogContent>
                    <DialogContentText>Add the jar name and opening time and
                        click "Create Jar" to add a jar to your collection!
                    </DialogContentText>
                    <TextField
                        required
                        autoFocus
                        margin="dense"
                        id="name"
                        label="Jar Name"
                        type="string"
                        fullWidth
                        variant="standard"
                        value={jarName}
                        onInput={(event) => { 
                            const v = event.target.value;
                             setJarName(v) } } />
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
                <Button onClick={handleCloseCreateJar}>Cancel</Button>
                    {jarName !== "" && jarOpeningTime !== "" ?
                    <Button onClick={handleCreateJar}>Create Jar</Button>
                    : null

                }
                </DialogActions>
            </Dialog></>
    )
}