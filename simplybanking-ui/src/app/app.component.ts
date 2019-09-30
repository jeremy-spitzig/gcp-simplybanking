import { Component } from '@angular/core';
import {AuthService} from "./security/auth.service";
import {User} from "./user/user.model";
import {Router} from "@angular/router";
import {skip} from "rxjs/operators";
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent {
  title = 'simplybanking-ui';
  currentUser:User = null;

  constructor(
    private authService:AuthService,
    private router:Router
  ) {
    skip<User>(1)(authService.currentUser)
      .subscribe((user) => {
        this.currentUser = user;
        if(this.currentUser == null) {
          this.router.navigate(["/"])
        }
      });
  }

  logout() {
    this.authService.logout()
  }
}
