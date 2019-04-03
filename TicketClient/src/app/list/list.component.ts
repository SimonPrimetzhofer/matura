import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Ticket } from '../ticket';
import { Observable } from 'rxjs/Rx';
import { DataService } from '../data.service';
import { Istate } from '../istate';
import { Ipriority } from '../ipriority';
import { Iuser } from '../iuser';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  public ticketList: Observable<Ticket[]>;
  public toInsert: Ticket = this.setTicketToDefault();

  //Dropdown elements
  public stateList: Observable<Istate[]>;
  public priorityList: Observable<Ipriority[]>;
  public userList: Observable<Iuser[]>;

  constructor(private http: HttpClient, private dataService: DataService) { }

  ngOnInit() {
    this.loadList();

    //Load dropdowns
    this.getStates();
    this.getPriorities();
    this.getUsers();

  }

  public loadList() {
    this.ticketList = this.dataService.getTickets();
  }

  public getStates() {
    this.stateList = this.dataService.getStates();
  }

  public getPriorities() {
    this.priorityList = this.dataService.getPriorities();
  }

  public getUsers() {
    this.userList = this.dataService.getUsers();
  }

  public deleteTicket(id: number, event: Event) {
    event.preventDefault();
    this.dataService.deleteTicket(id)
      .subscribe(() => {
        this.loadList();
      });
  }

  public addTicket() {

    if (this.toInsert.priorityID == 0 || this.toInsert.stateID == 0
      || this.toInsert.userID == 0 || this.toInsert.description.length < 1) {
      alert("Fill out every field!");
    } else {
      this.dataService.addTicket(this.toInsert)
        .subscribe(() => {
          this.toInsert.description = "";
          //this.setTicketToDefault();
          this.loadList();
        });
    }


  }

  public setState(stateName: string) {

    this.stateList.subscribe(states => {
      const state = states.find(s => s.stateName === stateName);
      this.toInsert.stateName = stateName;
      this.toInsert.stateID = state.id;
    });

  }

  public setPriority(priorityName: string) {
    this.priorityList.subscribe(priorities => {
      const priority = priorities.find(p => p.priorityName === priorityName);
      this.toInsert.priorityName = priorityName;
      this.toInsert.priorityID = priority.id;
    });
  }

  public setUser(userName: string) {
    this.userList.subscribe(users => {
      const user = users.find(u => u.userName === userName);
      this.toInsert.userName = userName;
      this.toInsert.userID = user.id;
    });
  }

  public setTicketToDefault() {
    return {
      id: undefined,
      description: "",
      submittedOn: new Date(),
      stateID: 0,
      stateName: "",
      priorityID: 0,
      priorityName: "",
      userID: 0,
      userName: "",
      firstName: "",
      lastName: ""
    };
  }

}
