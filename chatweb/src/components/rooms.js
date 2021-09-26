import React, { Component } from "react";
import axios from "axios";
import { Table, Button, Container, Form } from "react-bootstrap";
import Cookies from "js-cookie";

export default class Rooms extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loaded: false,
      chatRooms: []
    };
    this.api = "http://localhost:8080/api/rooms";
  }

  componentDidMount() {
    axios.get(this.api, {headers: {
      "Authorization": Cookies.get("Authorization")}
    })
    .then(res => {
      this.setState({
        chatRooms: [...res.data],
        loaded: true
      });
    })
    .catch(err => {
      console.log(err);
    });
  }

  roomList = () => {
    return this.state.chatRooms.map(room => {
      return (
        <tr key={room.id}>
          <td>{room.name}</td><td><Button type="button" onClick={() => {
            this.props.history.push(`/room/${room.id}`);
          }}>Join</Button></td>
        </tr>
      );
    })
  }

  render() {
    console.log(this.state.chatRooms);
    return (
      this.state.loaded ?
      <table>
        <tbody>
        {this.roomList()}
        </tbody>
      </table> : <div>Loading...</div>
    );
  }
}