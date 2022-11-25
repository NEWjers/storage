import { Component, OnInit } from '@angular/core';
import {AddSellComponent} from "../add-sell/add-sell.component";
import {MatDialog} from "@angular/material/dialog";
import {AddArrivalComponent} from "../add-arrival/add-arrival.component";

@Component({
  selector: 'app-board-user',
  templateUrl: './board-user.component.html',
  styleUrls: ['./board-user.component.css']
})
export class BoardUserComponent implements OnInit {

  constructor(
    private matDialog: MatDialog
  ) { }

  ngOnInit(): void {
  }

  onSell(enterAnimationDuration: string, exitAnimationDuration: string) {
    this.matDialog.open(AddSellComponent, {
      width: '75%',
      enterAnimationDuration,
      exitAnimationDuration
    });
  }

  onReceive(enterAnimationDuration: string, exitAnimationDuration: string) {
    this.matDialog.open(AddArrivalComponent, {
      width: '75%',
      enterAnimationDuration,
      exitAnimationDuration
    });
  }

}
