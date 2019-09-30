import { Injectable } from '@angular/core';
import {Headline} from "./headline.model";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class HeadlinesService {

  constructor(
    private http:HttpClient
  ) { }

  loadHeadlines() : Observable<Headline[]> {
    return this.http.get<Headline[]>(environment.baseUrl + "headline")
  }
}
