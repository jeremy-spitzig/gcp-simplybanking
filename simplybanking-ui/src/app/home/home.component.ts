import { Component, OnInit } from '@angular/core';
import {HeadlinesService} from "../headline/headlines.service";
import {Headline} from "../headline/headline.model";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass']
})
export class HomeComponent implements OnInit {

  headlines : Headline[];

  constructor(
    private headlinesService:HeadlinesService
  ) { }

  ngOnInit() {
    this.headlinesService.loadHeadlines()
      .subscribe((headlines) => {
        console.log(headlines);
        this.headlines = headlines;
      });
  }

}
