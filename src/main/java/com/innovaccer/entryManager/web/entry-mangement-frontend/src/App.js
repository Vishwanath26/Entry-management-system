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
      hideIcons: false
    };
    this.handleCheckInClick = this.handleCheckInClick.bind(this);
    this.handleCheckOutClick = this.handleCheckOutClick.bind(this);
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
            <br/>
            <br/>
            <br/>
            <br/>
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
          <Form toggleForms={this.handleCheckInClick.bind(this)} 
                formType="in"
           />
        ) : null}
        {this.state.showCheckOutForm ? (
          <Form toggleForms={this.handleCheckOutClick.bind(this)}
                formType="out"
           />
        ) : null}
      </div>
    );
  }
}

export default App;
