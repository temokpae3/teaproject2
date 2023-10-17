import React, { useEffect } from 'react';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Tooltip from '@mui/material/Tooltip';
import { IconButton } from '@mui/material';
import PropTypes from "prop-types";
import InfoOutlinedIcon from '@mui/icons-material/InfoOutlined';
import {Typography} from '@mui/material';
import CloseIcon from "@mui/icons-material/Close";
import ContentPasteIcon from '@mui/icons-material/ContentPaste';
import { styled } from '@mui/material/styles';



export default function JarStats(props) {
    const BootstrapDialog = styled(Dialog)(({ theme }) => ({
            "& .MuiDialogContent-root": {
            padding: theme.spacing(2)
        },
            "& .MuiDialogActions-root": {
            padding: theme.spacing(1)
        }
        }));
            const [open, setOpen] = React.useState(false);
            const handleClickOpen = () => {
            setOpen(true);
        };
            const handleClose = () => {
            setOpen(false);
        };
            const BootstrapDialogTitle = (props) => {
            const { children, onClose, ...other } = props;
            return (
                <DialogTitle sx={{ m: 0, p: 2 }} {...other}>
                {children}
                {onClose ? (
                <IconButton
                aria-label="close"
                onClick={onClose}
                sx={{
                position: "absolute",
                right: 8,
                top: 8,
                color: (theme) => theme.palette.grey[500]
        }}
        >
            <CloseIcon />
            </IconButton>
        ) : null}
            </DialogTitle>
            );
            };
            BootstrapDialogTitle.propTypes = {
            children: PropTypes.node,
            onClose: PropTypes.func.isRequired
        };


        

    const [jarId, setJarId] = React.useState(props.jarId);
    const [jars, setJarData] = React.useState(props.myJars);

     const getJar = () => {
         console.log(jars);
        for(let i = 0; i < jars.length; i++) {
            if(jars[i].jar_id === jarId) {
                return jars[i];
            }

        }
     }
  
    



    return(
        
        <><Tooltip title="View Jar Stats">
            <IconButton aria-label="jar stats" onClick={handleClickOpen}>
                <InfoOutlinedIcon />
            </IconButton>
        </Tooltip><BootstrapDialog
            onClose={handleClose}
            aria-labelledby="customized-dialog-title"
            open={open}
        >
                {console.log(getJar())}
                <BootstrapDialogTitle
                    id="customized-dialog-title"
                    onClose={handleClose}
                >
                    {getJar().name} Stats
                </BootstrapDialogTitle>
                <DialogContent dividers>
                    <Typography gutterBottom>Opening Date: {getJar().openingTime}</Typography>
                    <Typography gutterBottom>Total Notes: {getJar().numberOfNotes}</Typography>
                    <Typography gutterBottom>Total Collaborators: {getJar().collaborators.length}
                    </Typography>
                </DialogContent>
                <DialogActions>
                    <Button autoFocus onClick={handleClose}>
                        Close
                    </Button>
                </DialogActions>
            </BootstrapDialog></>
    )
}