import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import 'rxjs/Rx';
import { Observable } from 'rxjs/Rx';
import { Ticket } from '../ticket';
import { HttpClient } from '@angular/common/http';
import { Istate } from '../istate';
import { Ipriority } from '../ipriority';
import { Iuser } from '../iuser';
import { DataService } from '../data.service';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {

  public editID: Observable<string>;

  //Dropdown elements
  public stateList: Observable<Istate[]>;
  public priorityList: Observable<Ipriority[]>;
  public userList: Observable<Iuser[]>;

  public toUpdate: Ticket = {
    id: 0,
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

  constructor(private route: ActivatedRoute, private http: HttpClient,
    private router: Router, private dataService: DataService) { }

  ngOnInit() {
    this.editID = this.route.paramMap.map(param => param.get('id'));

    //Load dropdowns
    this.getStates();
    this.getPriorities();
    this.getUsers();

    this.editID.subscribe(res => this.toUpdate.id = +res);

    //Load ticket
    this.getTicket();
  }

  public getTicket() {

    this.dataService.getTicket(this.toUpdate.id)
      .subscribe(ticket => this.toUpdate = ticket);
  }

  public updateTicket() {

    this.dataService.updateTicket(this.toUpdate)
      .subscribe(() => {
        this.router.navigate(["home"]);
      });
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

  public setState(stateName: string) {

    this.stateList.subscribe(states => {
      const state = states.find(s => s.stateName === stateName);
      this.toUpdate.stateName = stateName;
      this.toUpdate.stateID = state.id;
    });

  }

  public setPriority(priorityName: string) {
    this.priorityList.subscribe(priorities => {
      const priority = priorities.find(p => p.priorityName === priorityName);
      this.toUpdate.priorityName = priorityName;
      this.toUpdate.priorityID = priority.id;
    });
  }

  public setUser(userName: string) {
    this.userList.subscribe(users => {
      const user = users.find(u => u.userName === userName);
      this.toUpdate.userName = userName;
      this.toUpdate.userID = user.id;
    });
  }

}
