import { Component, OnInit } from '@angular/core';
import {CreateUserRequest} from "./create-user-request.model";
import {RegisterService} from "./register.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.sass']
})
export class RegisterComponent implements OnInit {

  request:CreateUserRequest;

  constructor(
    private registerService:RegisterService
  ) {
    this.request = new CreateUserRequest();
  }

  ngOnInit() {
  }

  registerUser() {
    this.registerService.registerUser(this.request)
  }

}
