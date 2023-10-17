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
import NoteAddIcon from '@mui/icons-material/NoteAdd';
import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
export default function CreateNote(props) {

    const [openCreateNote, setOpenCreateNote] = React.useState(false);
    const [jarId, setJarId] = React.useState(props.jarId);
    const [message, setMessage] = React.useState("");
    const [rating, setRating] = React.useState(0);
    const [email, setEmail] = React.useState(props.email);
    
    const delay = ms => new Promise(res => setTimeout(res, ms));

    const handleClickCreateNote = () => {
        setOpenCreateNote(true);
    };

    const handleCloseCreateNote = () => {
        setOpenCreateNote(false);
    };

    const handleCreateNote = async () => {
    let currentDate = new Date();
      
      const note = {
          message: message,
          charNum: message.length,
          time: currentDate.toISOString().slice(0,10),
          rating: rating,
          author: email,
          note_id: 1234,
          jar_id: parseInt(jarId)
      }

      const requestOptions = {
          method: 'POST',
          body: JSON.stringify(note)
      };
      const r = fetch('http://localhost:9080/api/Note/' + localStorage.getItem("token_id"), requestOptions);
      
      setOpenCreateNote(false);

      await delay(1000);
      window.location.reload();

    };


    return(
        <><Tooltip title="Add Note"><IconButton aria-label="add note"
            onClick={handleClickCreateNote}>
            <NoteAddIcon />
        </IconButton></Tooltip><Dialog open={openCreateNote} onClose={handleCloseCreateNote}>
                <DialogTitle>Add Note</DialogTitle>
                <DialogContent>
                    <DialogContentText>Add your message and rating to add a note to the jar (256 character limit)!
                    </DialogContentText>
                    <TextField
                        required
                        multiline
                        autoFocus
                        margin="dense"
                        id="name"
                        label="Note"
                        type="string"
                        fullWidth
                        variant="standard"
                        inputProps= {{maxLength: 256}}
                        value={message}
                        onInput={(event) => { 
                            const v = event.target.value;
                             setMessage(v) } } />

            <Box sx={{ minWidth: 120 }}>
                <FormControl fullWidth>
                    <InputLabel id="Select Rating">Rating</InputLabel>
                    <Select
                    labelId="Select Rating"
                    id="Select Rating"
                    value={rating}
                    label="Rating"
                    onChange={(event) => {
                        console.log("Rating:" + event.target.value);
                        setRating(event.target.value)
                    }}
                    >
                    <MenuItem value={1}>1</MenuItem>
                    <MenuItem value={2}>2</MenuItem>
                    <MenuItem value={3}>3</MenuItem>
                    <MenuItem value={4}>4</MenuItem>
                    <MenuItem value={5}>5</MenuItem>
                    </Select>
                </FormControl>
                </Box>
                    
     
                </DialogContent>
                <DialogActions>
                <Button onClick={handleCloseCreateNote}>Cancel</Button>
                    {message !== "" && rating !== 0 ?
                    <Button onClick={handleCreateNote}>Add Note</Button>
                    : null

                }
                </DialogActions>
            </Dialog></>
    )
}