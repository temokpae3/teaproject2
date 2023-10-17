import React, {useEffect} from 'react';
import PropTypes from 'prop-types';
import List from '@mui/material/List';
import DialogTitle from '@mui/material/DialogTitle';
import Dialog from '@mui/material/Dialog';
import IconButton from '@mui/material/IconButton';
import ContentPasteIcon from '@mui/icons-material/ContentPaste';
import { DataGrid, GridToolbar } from '@mui/x-data-grid';
import DeleteIcon from '@mui/icons-material/Delete';
import Box from '@mui/material/Box';
import Tooltip from '@mui/material/Tooltip';
import { makeStyles } from '@mui/styles';

function SimpleDialog(props) {
  const [selectionModel, setSelectionModels] = React.useState([]);
  const [reload, setReload] = React.useState(false);
  const { onClose, selectedValue, open } = props;
  const [userId, setUserId] = React.useState(props.userId);
  const [jar, setJar] = React.useState(props.jar);
  const [notes, setNotes] = React.useState([]);

  const delay = ms => new Promise(res => setTimeout(res, ms));
  useEffect(() => {
       getNotesJsonData();
    },[]);

  //const [notesJson, setNotesJson] = React.useState([]);
  //const notesJson = Notes;

  /**
   * TO-DO: Add export jar review data grid thingy in here
   */

  const handleClose = async () => {
    onClose(selectedValue);
  };

  const handleDeleteNote = async () => {
    const selectedIDs = new Set(selectionModel);

    selectedIDs.forEach(function(noteId) {
      fetch('http://localhost:9080/api/Note/note_id/' + noteId + '/' + userId, {
           method: "DELETE"
       });
    })

    onClose(selectedValue);
    await delay(1000);
      window.location.reload();
  }

  const columns = [
    {
      field: 'message', headerName: 'Message', width: 500
    },
    {
      field: 'time',
      headerName: 'Date',
      width: 100,
      editable: true,
    },
    {
      field: 'rating',
      headerName: 'Rating',
      width: 80,
      editable: true,
    },
    {
      field: 'author',
      headerName: 'Author',
      type: 'string',
      width: 200,
      editable: true,
    },
  ];

  const getNotesJsonData = async () => {
    if(userId !== jar.jarAdmin && jar.closed) {
      const response = await fetch('http://localhost:9080/api/Note/jar_id/' + jar.jar_id + '/' + userId, {
           method: "GET"
       });
       const jsonData = await response.json();
       //console.log(jsonData);
       for(let i = 0; i < jsonData.length; i++) {
        jsonData[i] = JSON.parse(jsonData[i]);
      }
      setNotes(jsonData);
       
      
    } else {
        const response = await fetch('http://localhost:9080/api/Note/jar_id/' + jar.jar_id, {
           method: "GET"
       })
       const jsonData = await response.json();
       //console.log(jsonData);
       for(let i = 0; i < jsonData.length; i++) {
        jsonData[i] = JSON.parse(jsonData[i]);
      }
      setNotes(jsonData);
       
    }
    
  }

  const rows = notes;
  //console.log("Rows: " + rows);

  return (
    <Dialog
    fullWidth={true}
    maxWidth="lg"
    onClose={handleClose} open={open}>
      <DialogTitle>
        <Box display="flex" alignItems="center">
            <Box flexGrow={1}> Notes </Box>
            <Box>
                <Tooltip title='Delete Note(s)'><IconButton
                onClick={handleDeleteNote}>
                    <DeleteIcon />
                </IconButton></Tooltip>
            </Box>
        </Box>
      </DialogTitle>
      <List sx={{ pt: 0 }}>
          <div style={{ height: 400, width: '100%' }}>
                <DataGrid
                  getRowId = {row => row.note_id}
                  rows={rows}
                  columns={columns}
                  pageSize={5}
                  rowsPerPageOptions={[5]}
                  components={{ Toolbar: GridToolbar }}
                  checkboxSelection
                  disableSelectionOnClick
                  onSelectionModelChange={(ids) => {
                    setSelectionModels(ids);
                  }}
                />
              </div>
      </List>
    </Dialog>
  );
}

SimpleDialog.propTypes = {
  onClose: PropTypes.func.isRequired,
  open: PropTypes.bool.isRequired,
  selectedValue: PropTypes.string.isRequired,
};

export default function SimpleDialogDemo(props) {
  const [open, setOpen] = React.useState(false);
  const [selectedValue, setSelectedValue] = React.useState();
  const [userId, setUserId] = React.useState(props.userId);
  const [jar, setJar] = React.useState(props.jar);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = (value) => {
    setOpen(false);
    setSelectedValue(value);
  };

  return (
    <div>
      <ContentPasteIcon sx={{ m: 0.5 }} onClick={handleClickOpen} />
      <SimpleDialog
        selectedValue={selectedValue}
        open={open}
        onClose={handleClose}
        userId={userId}
        jar={jar}
      />
    </div>
  );
}