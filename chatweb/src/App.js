import './App.css';
import React, { Component }  from 'react';
import { Switch, Route } from "react-router-dom";
import Login from './components/login';
import Rooms from './components/rooms';
import ChatRoom from './components/chatRoom';

function App() {
  return (
    <div className="App">
        <Switch>
          <Route exact path="/login" component={Login} />
          <Route exact path="/rooms" component={Rooms} />
          <Route path="/room/:id" component={ChatRoom} />
        </Switch>
  
        <link
          rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossOrigin="anonymous"
        />
    </div>
  );
}

export default App;
