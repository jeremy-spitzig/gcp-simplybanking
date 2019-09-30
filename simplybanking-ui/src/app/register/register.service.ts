import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CreateUserRequest} from "./create-user-request.model";

import { environment } from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http: HttpClient) { }

  registerUser(request:CreateUserRequest) {
    return this.http.post(environment.baseUrl + "user/register", request);
  }
}
