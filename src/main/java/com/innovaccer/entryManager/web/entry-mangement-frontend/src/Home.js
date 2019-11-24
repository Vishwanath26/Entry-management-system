import React from "react";
import Avatar from "@material-ui/core/Avatar";
import Button from "@material-ui/core/Button";
import CssBaseline from "@material-ui/core/CssBaseline";
import TextField from "@material-ui/core/TextField";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import Link from "@material-ui/core/Link";
import Grid from "@material-ui/core/Grid";
import Box from "@material-ui/core/Box";
import LockOutlinedIcon from "@material-ui/icons/LockOutlined";
import Typography from "@material-ui/core/Typography";
import { makeStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";
import { ValidatorForm, TextValidator } from "react-material-ui-form-validator";
import "./Form.css";

class Form extends React.Component {
  constructor() {
    super();
    this.state = {
      visitorName: "",
      visitorEmail: "",
      visitorPhone: "",
      hostName: "",
      hostEmail: "",
      hostPhone: "",
      formErrors: {
        visitorName: "",
        visitorEmail: "",
        visitorPhone: "",
        hostName: "",
        hostEmail: "",
        hostPhone: ""
      },
      visitorNameValid: false,
      visitorEmailValid: false,
      visitorPhoneValid: false,
      hostNameValid: false,
      hostEmailValid: false,
      hostPhoneValid: false,
      formValid: false
    };
    this.handleChange = this.handleChange.bind(this);
    this.validateField = this.validateField.bind(this);
    this.setMeeting = this.setMeeting.bind(this);
  }

  //Backend call for scheduling meeting
  setMeeting() {
    let meetingUrl = "http://localhost:8080/meeting/set";
    let meetingRequest = {
      visitorDto: {
        visitorName: this.state.visitorName,
        visitorEmailId: this.state.visitorEmail,
        visitorPhoneNum: this.state.visitorPhone
      },

      hostDto: {
        hostName: this.state.hostName,
        hostEmailId: this.state.hostEmail,
        hostPhoneNumber: this.state.hostPhone
      }
    };
    //fetch api call

    fetch(meetingUrl, {
      method: "POST",
      headers: {
        Accept: "application/json, text/plain",
        "Content-Type": "application/json;charset=UTF-8"
      },
      body: JSON.stringify(meetingRequest)
    })
      .then(response => response.text())
      .then(data => console.log(data))
      .catch(error => console.log("Error detected: " + error));

    this.props.toggleForms();
  }
  validateField(fieldName, value) {
    let fieldValidationErrors = this.state.formErrors;
    let visitorNameValid = this.state.visitorNameValid;
    let visitorEmailValid = this.state.visitorEmailValid;
    let visitorPhoneValid = this.state.visitorPhoneValid;
    let hostNameValid = this.state.hostNameValid;
    let hostEmailValid = this.state.hostEmailValid;
    let hostPhoneValid = this.state.hostPhoneValid;
    switch (fieldName) {
      case "visitorName":
        visitorNameValid = value.match(/[a-zA-Z]/);
        fieldValidationErrors.visitorName = visitorNameValid ? true : false;
        break;
      case "hostName":
        hostNameValid = value.match(/[a-zA-Z]/);
        fieldValidationErrors.hostName = hostNameValid ? true : false;
        break;
      case "visitorEmail":
        visitorEmailValid = value.match(/^([\w.%+-]+)@([\w-]+\.)+([\w]{2,})$/i);
        fieldValidationErrors.visitorEmail = visitorEmailValid ? true : false;
        break;
      case "hostEmail":
        hostEmailValid = value.match(/^([\w.%+-]+)@([\w-]+\.)+([\w]{2,})$/i);
        fieldValidationErrors.hostEmail = hostEmailValid ? true : false;
        break;
      case "visitorPhone":
        visitorPhoneValid = value.length === 10 && value.match(/[0-9]/);
        fieldValidationErrors.visitorPhone = visitorPhoneValid ? true : false;
        break;
      case "hostPhone":
        hostPhoneValid = value.length === 10 && value.match(/[0-9]/);
        fieldValidationErrors.hostPhone = hostPhoneValid ? true : false;
        break;
      default:
        break;
    }
    this.setState(
      {
        formErrors: fieldValidationErrors,
        visitorNameValid: visitorNameValid,
        visitorEmailValid: visitorEmailValid,
        visitorPhoneValid: visitorPhoneValid,
        hostNameValid: hostNameValid,
        hostEmailValid: hostEmailValid,
        hostPhoneValid: hostPhoneValid
      },
      this.validateForm
    );
  }
  validateForm() {
    this.setState({
      formValid:
        this.state.visitorNameValid &&
        this.state.visitorEmailValid &&
        this.state.visitorPhoneValid &&
        this.state.hostNameValid &&
        this.state.hostEmailValid &&
        this.state.hostPhoneValid
    });
  }

  errorClass(error) {
    return error.length === 0 ? "" : "has-error";
  }

  handleChange = e => {
    const name = e.target.name;
    const value = e.target.value;

    this.setState({ [name]: value }, () => {
      this.validateField(name, value);
    });
    //console.log(this.state.formValid);
  };

  render() {
    return (
      <Container component="main" maxWidth="xs" backgroundColor="white">
        <CssBaseline />
        <div className="paper">
          <form className="form" noValidate>
            {this.props.formType === "in" ? (
              <Grid container spacing={1}>
                <Grid item xs={12}>
                  <TextField
                    color={
                      this.state.visitorNameValid ? "primary" : "secondary"
                    }
                    name="visitorName"
                    variant="outlined"
                    required
                    fullWidth
                    onChange={this.handleChange}
                    id="Visitor-Name"
                    label="Visitor Name"
                    autoFocus
                  />
                </Grid>{" "}
                <Grid item xs={12}>
                  <TextField
                    color={
                      this.state.visitorEmailValid ? "primary" : "secondary"
                    }
                    variant="outlined"
                    required
                    fullWidth
                    onChange={this.handleChange}
                    id="visitor-email"
                    label="Visitor Email Address"
                    name="visitorEmail"
                  />
                </Grid>{" "}
                <Grid item xs={12}>
                  <TextField
                    color={
                      this.state.visitorPhoneValid ? "primary" : "secondary"
                    }
                    variant="outlined"
                    required
                    fullWidth
                    onChange={this.handleChange}
                    name="visitorPhone"
                    label="Visitor Phone"
                    type="phone"
                    id="vistor-phone"
                  />
                </Grid>{" "}
                <Grid item xs={12}>
                  <TextField
                    color={this.state.hostNameValid ? "primary" : "secondary"}
                    name="hostName"
                    variant="outlined"
                    required
                    fullWidth
                    onChange={this.handleChange}
                    id="Host-Name"
                    label="Host Name"
                    autoFocus
                  />
                </Grid>{" "}
                <Grid item xs={12}>
                  <TextField
                    color={this.state.hostEmailValid ? "primary" : "secondary"}
                    variant="outlined"
                    required
                    fullWidth
                    id="host-email"
                    label="Host Email Address"
                    name="hostEmail"
                    onChange={this.handleChange}
                  />
                </Grid>{" "}
                <Grid item xs={12}>
                  <TextField
                    color={this.state.hostPhoneValid ? "primary" : "secondary"}
                    variant="outlined"
                    required
                    fullWidth
                    name="hostPhone"
                    label="Host Phone"
                    type="phone"
                    id="host-phone"
                    onChange={this.handleChange}
                  />
                </Grid>{" "}
              </Grid>
            ) : (
              <Grid container spacing={1}>
                <Grid item xs={12}>
                  <TextField
                    color={
                      this.state.visitorNameValid ? "primary" : "secondary"
                    }
                    name="visitorName"
                    variant="outlined"
                    required
                    fullWidth
                    onChange={this.handleChange}
                    id="Visitor-Name"
                    label="Visitor Name"
                    autoFocus
                  />
                </Grid>{" "}
                <Grid item xs={12}>
                  <TextField
                    color={
                      this.state.visitorEmailValid ? "primary" : "secondary"
                    }
                    variant="outlined"
                    required
                    fullWidth
                    onChange={this.handleChange}
                    id="visitor-email"
                    label="Visitor Email Address"
                    name="visitorEmail"
                  />
                </Grid>{" "}
                <Grid item xs={12}>
                  <TextField
                    color={
                      this.state.visitorPhoneValid ? "primary" : "secondary"
                    }
                    variant="outlined"
                    required
                    fullWidth
                    onChange={this.handleChange}
                    name="visitorPhone"
                    label="Visitor Phone"
                    type="phone"
                    id="vistor-phone"
                  />
                </Grid>{" "}
              </Grid>
            )}
            <br />
            <Button
              // disabled={
              //   !(this.props.formType === "in"
              //     ? this.state.formValid
              //     : this.state.visitorNameValid &&
              //       this.state.visitorEmailValid &&
              //       this.state.visitorPhoneValid)
              // }
              onClick={this.setMeeting}
              maxWidth
              variant="contained"
              color="primary"
              className="submit"
            >
              Submit{" "}
            </Button>{" "}
          </form>{" "}
        </div>{" "}
      </Container>
    );
  }
}
export default Form;
