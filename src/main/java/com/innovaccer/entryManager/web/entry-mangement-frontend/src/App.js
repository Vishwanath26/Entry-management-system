import React from "react";
import logo from "../src/HomePage.jpg";
import "./App.css";
import Form from "./Home";
import Button from "@material-ui/core/Button";

class App extends React.Component {
  constructor() {
    super();
    this.state = {
      showCheckInForm: false,
      showCheckOutForm: false,
      hideIcons: false,
      checkInSuccess: false,
      checkInFailure: false,
      checkOutSuccess: false,
      checkOutFailure: false
    };
    this.handleCheckInClick = this.handleCheckInClick.bind(this);
    this.handleCheckOutClick = this.handleCheckOutClick.bind(this);
    (this.handleCheckInSuccess = this.handleCheckInSuccess).bind(this);
    this.handleCheckInFailure = this.handleCheckInFailure.bind(this);
    this.handleCheckOutSuccess = this.handleCheckOutSuccess.bind(this);
    this.handleCheckOutFailure = this.handleCheckOutFailure.bind(this);
  }

  handleCheckInClick() {
    this.setState({
      showCheckInForm: !this.state.showCheckInForm,
      hideIcons: !this.state.hideIcons
    });
  }

  handleCheckOutClick() {
    this.setState({
      showCheckOutForm: !this.state.showCheckOutForm,
      hideIcons: !this.state.hideIcons
    });
  }

  handleCheckInSuccess() {
    console.log("reached here");

    this.setState({
      checkInSuccess: true
    });
  }
  handleCheckInFailure() {
    this.setState({
      checkInFailure: true
    });
  }
  handleCheckOutSuccess() {
    this.setState({
      checkOutSuccess: true
    });
  }
  handleCheckOutFailure() {
    this.setState({
      checkOutFailure: true
    });
  }
  render() {
    return (
      <div className="App">
        <img src={logo} className="App-logo" alt="logo" />{" "}
        {this.state.hideIcons ? null : (
          <div className="wrapper">
            <Button
              className="btn"
              type="submit"
              size="large"
              variant="contained"
              color="primary"
              onClick={this.handleCheckInClick}
            >
              Check-In{" "}
            </Button>{" "}
            <br />
            <br />
            <br />
            <br />
            <Button
              className="btn"
              type="submit"
              size="large"
              variant="contained"
              color="primary"
              onClick={this.handleCheckOutClick}
            >
              Check-Out{" "}
            </Button>{" "}
          </div>
        )}{" "}
        {this.state.showCheckInForm ? (
          <Form
            toggleForms={this.handleCheckInClick.bind(this)}
            formType="in"
            checkInSuccess={this.handleCheckInSuccess.bind(this)}
            checkInFailure={this.handleCheckInFailure.bind(this)}
          />
        ) : null}
        {this.state.showCheckOutForm ? (
          <Form
            toggleForms={this.handleCheckOutClick.bind(this)}
            formType="out"
            checkOutSuccess={this.handleCheckOutSuccess.bind(this)}
            checkOutFailure={this.handleCheckOutFailure.bind(this)}
          />
        ) : null}
        {this.state.checkInSuccess ? <h1>"Check-In is successful"</h1> : null}
        {this.state.checkInFailure ? <h1>"Please Check-Out first"</h1> : null}
        {this.state.checkOutSuccess ? <h1>"Check-Out is successful"</h1> : null}
        {this.state.checkOutFailure ? <h1>"Please Check-In first"</h1> : null}
      </div>
    );
  }
}

export default App;
