import React from 'react';
import Home from './Home';
import { Outlet } from 'react-router-dom';

const HomeLayout = () => (
    <>
    <Home />
    <Outlet />
    </>
);

export default HomeLayout;