import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { environment } from "../../environments/environment";
import {BehaviorSubject, EMPTY, empty, Observable, pipe} from 'rxjs';
import { User } from "../user/user.model";
import {tryCatch} from "rxjs/internal-compatibility";
import {catchError} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private currentUserSubject = new BehaviorSubject<User>(null);

  constructor(
    private http : HttpClient
  ) {
    this.loadCurrentUser()
  }

  get currentUser() : Observable<User> {
    return this.currentUserSubject.asObservable()
  }

  authenticate(
    username:string,
    password:string
  ) : Observable<User> {
    const formData = new FormData();
    formData.set("username", username);
    formData.set("password", password);
    const login = this.http.post<User>(environment.baseUrl + "login", formData);
    login.subscribe((user:User)=> {
      this.currentUserSubject.next(user)
    });
    return login;
  }

  logout() : Observable<any> {
    const logout = this.http.post<any>(environment.baseUrl + "logout", null);
    logout.subscribe((_) =>
      this.currentUserSubject.next(null));
    return logout;
  }

  private loadCurrentUser() {
    catchError((err, caught) => EMPTY)
      (this.http.get(environment.baseUrl + "auth/currentUser"))
      .subscribe((user:User)=> {
        this.currentUserSubject.next(user)
      })
  }
}
