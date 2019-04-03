import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Iuser } from './iuser';
import { Ipriority } from './ipriority';
import { Istate } from './istate';
import { Ticket } from './ticket';

@Injectable()
export class DataService {

  public URL = "http://localhost:8080/MaturaExcercise_Server/rest/";

  constructor(private http: HttpClient) { }

  public getStates() {
    return this.http.get<Istate[]>(this.URL + "states/");
  }

  public getPriorities() {
    return this.http.get<Ipriority[]>(this.URL + "priorities/");
  }

  public getUsers() {
    return this.http.get<Iuser[]>(this.URL + "users/");
  }

  public getTicket(id: number) {
    return this.http.get<Ticket>(this.URL + "tickets/" + id);
  }

  public getTickets() {
    return this.http.get<Ticket[]>(this.URL + "tickets/");
  }

  public updateTicket(toUpdate: Ticket) {
    const today = new Date();
    toUpdate.submittedOn = new Date();
    return this.http.put(this.URL + "tickets/" + toUpdate.id, toUpdate)
  }

  public deleteTicket(id: number) {
    return this.http.delete(this.URL + "tickets/" + id);
  }

  public addTicket(toInsert: Ticket) {
    return this.http.post(this.URL + "tickets/", toInsert);
  }


}
