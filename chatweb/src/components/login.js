import React, { Component } from "react";
// import axios from "axios";
import { Table, Button, Container, Form } from "react-bootstrap";
import Cookies from 'js-cookie';
import axios from "axios";

export default class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: ""
    };
    this.api = "http://localhost:8080/auth/login";
    this.headers = {
      'Access-Control-Allow-Origin': 'http://localhost:3000'
    }
  }

  onChangeUsername = e => {
    this.setState({username: e.target.value});
  }

  onSubmit = e => {
    console.log('submitted');
    e.preventDefault();
    axios.post(this.api, {
      username: this.state.username
    })
    .then(res => {
      console.log(res.headers.authorization);
      if (res.status === 200) {
        Cookies.set("Authorization", res.headers.authorization);
        this.props.history.push('/rooms');
      }
    })
    .catch(err => console.log(err));
  }

  render() {
    return (
      <Container>
      <Form onSubmit={this.onSubmit}>
        <Form.Control name="username" type="text" onChange={this.onChangeUsername} value={this.state.username} required />
        <Button variant="primary" size="block" type="submit">
          Login
        </Button>
      </Form>
      </Container>
    );
  }
}