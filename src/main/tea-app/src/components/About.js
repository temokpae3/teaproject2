import React from "react";
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Grid from '@mui/material/Grid';
import List from '@mui/material/List';
import ListItemText from '@mui/material/ListItemText';
import ListItem from '@mui/material/ListItem';

function About() {
    return (
        <Box sx={{ width: '100%', justifyContent: 'center' }}>
            <Typography
                paddingLeft= "230px"
                sx={{ textAlign: 'center', m: 3, }}
                variant="body1" component="div">
                One thing that we can perhaps all agree on is that during the pandemic, work, life,
                <div />
                and work/life balance hasn’t exactly been made easy for all. It’s hard sometimes to motivate yourself
                <div />
                to get up and function. And the consequence is that from time to time, we fall into a slump of
                <div />
                pessimistic thoughts and lack of enthusiasm.
                <div />
                This is where Positivitea can help!
            </Typography>
            <Typography
                paddingLeft= "230px"
                sx={{ textAlign: 'center', m: 3, }}
                variant="body1" component="div">
                Inspired by one of those physical mason jars into which you and your friends or
                <div />
                family keep positive thoughts, our interactive Positivitea webapp aims to maintain a virtual vessel for
                <div />
                the shared positive thoughts and encouraging moments that you and your loved ones may keep for one another.
                <div />
                Every time something nice happens that you'd like to remember, write (or in this case type) you thoughts
                <div />
                and musings in a brief message and we'll save the date and toss it into your very own Positivitea jar. Then,
                <div />
                at the end of the year, thr group sits together for a *Jar Opening Ceremony* where everyone gets to share
                <div />
                in the memories!
            </Typography>
            <Typography
                paddingLeft= "230px"
                sx={{ textAlign: 'center', m: 3, }}
                variant="body1" component="div">
                The mission of Positivitea is to create a zen and approachable way for our
                <div />
                users to practice mindfulness in their every day lives, in a format that caters specifically to those
                <div />
                who cannot meet together in person. A traditional positivity jar is, in essence, a group mindfulness
                <div />
                activity where participants can share in positive moments together. The Positivitea is being created to help
                <div />
                users practice this mindfulness with friends online. Especially during times like the Pandemic, we all go
                <div />
                through negative mental health stages and distance can lead to strains on important relationships with others.
                <div />
                Attempting to manage a <i>physical</i> mason jar can be a hassle. Consider Positivitea a tool for increasing
                <div />
                social connectedness and mindfulness habits, achieved through a web version of the classic positivity jar
                <div />
                group activity.
            </Typography>
            <Typography
                paddingLeft= "200px"
                sx={{ textAlign: 'center', m: 1, }}
                variant="h5" component="div">
                So why 'Positivi-TEA'?
            </Typography>
            <Typography
                paddingLeft= "230px"
                sx={{ textAlign: 'center', m: 3, }}
                variant="body1" component="div">
                Ah yes, as I'm sure you've picked up on by now, the actual activity we're discussing has nothing to do with
                <div />
                hot leaf juice. As a brand, we wanted to emphasize the mindfulness and zen of our project, and come up with
                <div />
                an original concept for this well known activity. Tea is just a tiny taste of hundreds of little leaves,
                <div />
                preserved and shared with others. We like to think of each positivity note as a leaf; a moment of personal
                <div />
                growth you keep and cherish. Over time many small moments of personal growth all meet together, being
                <div />
                made stronger with the heat and stress of daily life, and over time finally shared with others on a rainy day.
                <div />
            </Typography>
            <Typography
                paddingLeft= "200px"
                sx={{ textAlign: 'center', m: 1, }}
                variant="h5" component="div">
                Credits
            </Typography>
            <Typography
                paddingLeft= "245px"
                sx={{ textAlign: 'center', m: 1, }}
                variant="body1" component="div">
                This product was the creation of Team I, a group of students at SUNY Oswego inspired
                <div />
                by an assignment for Professor Bastian Tenbergen for CSC 380: Software Engineering.
                <div />
                Those on the team include:
                <Grid container justifyContent="center" >
                  <List>
                    <ListItem component="a" href="kgray3@oswego.edu">
                        <ListItemText primary="Kayla Gray" />
                    </ListItem>
                    <ListItem component="a" href="jchimbo@oswego.edu">
                        <ListItemText primary="Jeremi Chimbo" />
                    </ListItem>
                    <ListItem component="a" href="vliu2@oswego.edu">
                        <ListItemText primary="Vicky Liu" />
                    </ListItem>
                    <ListItem component="a" href="temokpae@oswego.edu">
                        <ListItemText primary="Temitope Emokpae" />
                    </ListItem>
                    <ListItem component="a" href="swong3@oswego.edu">
                        <ListItemText primary="Sarah Wong" />
                    </ListItem>
                 </List>
                </Grid>
                </Typography>
                <Typography
                    paddingLeft= "245px"
                    sx={{ textAlign: 'center', m: 1, }}
                    variant="body1" component="div">
                    The lovely logo and jar artwork was donated by <a href={"https://www.linkedin.com/in/kayla-viti-4142aa1a6"}>
                    Kayla Viti</a>, who's a senior at Utica University
                    and is available to be contacted through her LinkedIn at the attached link.

                    The jar opening ceremony animations were donated by <a href={"https://cmvander63.wixsite.com/catportfolio"}> Cat Vandermark</a>,
                    an aspiring animator from New Hartford, NY.
                </Typography>
            </Box>
    );
}

export default About;