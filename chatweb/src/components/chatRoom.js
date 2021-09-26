import React, { Component } from "react";
import axios from "axios";
import { Table, Button, Container, Form } from "react-bootstrap";
import Cookies from "js-cookie";
import SockJsClient from 'react-stomp';

export default class ChatRoom extends Component {
  constructor(props) {
    super(props);
    this.state = {
      messages: [],
      inputMessage: "",
      clear: false
    };
    this.roomId = this.props.match.params.id;
    
    this.api = `http://localhost:8000/chat/api/chat/${this.roomId}`;
    this.endPoint = 'http://localhost:9001/ws';
    this.topic =  `/topic/${this.roomId}`;
    this.destination = `/app/${this.roomId}`;
  }

  onChangeInputMessage = e => {
    this.setState({inputMessage: e.target.value});
  }

  onSubmit = e => {
    e.preventDefault();
    this.sendMessage();
    e.target.value = "";
  }

  sendMessage = () => {
    const messageContent = this.state.inputMessage.trim();
    if (messageContent && this.clientRef) {
      this.clientRef.sendMessage(`${this.destination}/chat`,
        JSON.stringify({
          userId: Cookies.get("ID"),
          text: this.state.inputMessage,
          type: 'CHAT'
        }));
    }
  }

  onConnect = () => {
    this.clientRef.sendMessage(`${this.destination}/join`, 
      JSON.stringify({
        userId: Cookies.get("ID"),
        type: 'JOIN'
      }));
    console.log('Connected');
  }

  onDisconnect = () => {
    console.log('Disconnected');
  }

  onMessage = message => {
    this.setState({
      messages: [...this.state.messages, message]
    });
    
    console.log(message);
  }

  componentDidMount() {
    axios.get(this.api, {headers: {
      "Authorization": `Bearer ${Cookies.get("token")}`}
    })
    .then(res => {
      this.setState({
        messages: [...res.data]
      });
    })
    .catch(err => {
      console.log(err);
    });
  }

  messageList = () => {
    return this.state.messages.map(message => {
      return (
        <li id={message.id}>
          <strong className="nickname">{message.userId}</strong>
          <span>{message.text}</span>
        </li>
      );
    })
  }

  render() {
    return (
      <div id="chat-container">
        <SockJsClient url={this.endPoint} topics={[this.topic]}
          onMessage={this.onMessage}
          onConnect={this.onConnect}
          onDisconnect={this.onDisconnect}
          ref={ client => {
            this.clientRef = client;
          }} />
        <div className="chat-header">
          <div className="user-container">
          </div>
          <h3 id="roomId">{this.roomId}</h3>
        </div>
        <hr/>
        <ul id="messageArea">
          {this.messageList()}
        </ul>
        <form id="messageForm" name="messageForm" onSubmit={this.onSubmit}>
          <div className="input-message">
            <input type="text" id="message" onChange={this.onChangeInputMessage} autoComplete="off"
                  placeholder="Type a message..."/>
            <button type="submit">Send</button>
          </div>
        </form>
      </div>
    );
  }
}