import { Component, OnInit } from '@angular/core';
import {Arrival} from "../dto/Arrival";
import {MatDialog} from "@angular/material/dialog";
import {ArrivalService} from "../_services/arrival.service";
import {AddArrivalComponent} from "../add-arrival/add-arrival.component";
import {ViewArrivalComponent} from "../view-arrival/view-arrival.component";
import {PageEvent} from "@angular/material/paginator";
import {Sort} from "@angular/material/sort";

@Component({
  selector: 'app-board-arrival',
  templateUrl: './board-arrival.component.html',
  styleUrls: ['./board-arrival.component.css']
})
export class BoardArrivalComponent implements OnInit {

  arrivals?: Arrival[];

  totalElements: number = 0;

  currentPage: number = 0;

  currentSize: number = 13;

  currentSort: string = '';

  currentWay: string = '';

  id: string = '';

  date: string = '';

  user: string = '';

  constructor(
    private matDialog: MatDialog,
    private arrivalService: ArrivalService
  ) { }

  ngOnInit(): void {
    this.arrivalService.getArrivalsPage(0, 13, 'id', 'asc', this.id, this.date, this.user).subscribe(
      data => {
        this.arrivals = data;
      }
    );

    this.arrivalService.getArrivalsSize(this.id, this.date, this.user).subscribe(
      data => {
        this.totalElements = data;
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

  nextPage(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.currentSize = event.pageSize;
    this.arrivalService.getArrivalsPage(event.pageIndex, event.pageSize, this.currentSort, this.currentWay, this.id, this.date, this.user).subscribe(
      data => {
        this.arrivals = data;
      }
    );
  }

  sortData(sort: Sort) {
    this.currentSort = sort.active;
    this.currentWay = sort.direction;
    this.arrivalService.getArrivalsPage(this.currentPage, this.currentSize, sort.active, sort.direction, this.id, this.date, this.user).subscribe(
      data => {
        this.arrivals = data;
      }
    );
  }

  onFilter() {
    this.arrivalService.getArrivalsPage(this.currentPage, this.currentSize, this.currentSort, this.currentWay, this.id, this.date, this.user).subscribe(
      data => {
        this.arrivals = data;
      }
    );

    this.arrivalService.getArrivalsSize(this.id, this.date, this.user).subscribe(
      data => {
        this.totalElements = data;
      }
    );
  }
}
