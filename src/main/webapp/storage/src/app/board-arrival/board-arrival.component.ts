import { Component, OnInit } from '@angular/core';
import {Arrival} from "../dto/Arrival";
import {MatDialog} from "@angular/material/dialog";
import {ArrivalService} from "../_services/arrival.service";
import {AddArrivalComponent} from "../add-arrival/add-arrival.component";
import {ViewArrivalComponent} from "../view-arrival/view-arrival.component";

@Component({
  selector: 'app-board-arrival',
  templateUrl: './board-arrival.component.html',
  styleUrls: ['./board-arrival.component.css']
})
export class BoardArrivalComponent implements OnInit {

  arrivals?: Arrival[];

  constructor(
    private matDialog: MatDialog,
    private arrivalService: ArrivalService
  ) { }

  ngOnInit(): void {
    this.arrivalService.getAllArrivals().subscribe(
      data => {
        this.arrivals = data;
      }
    );
  }

  addArrivalDialog(enterAnimationDuration: string, exitAnimationDuration: string) {
    this.matDialog.open(AddArrivalComponent, {
      width: '75%',
      enterAnimationDuration,
      exitAnimationDuration
    });
  }

  viewArrivalDialog(enterAnimationDuration: string, exitAnimationDuration: string, arrival: Arrival) {
    this.matDialog.open(ViewArrivalComponent, {
      width: '75%',
      enterAnimationDuration,
      exitAnimationDuration,
      data: {
        arrival: arrival
      }
    });
  }

}
