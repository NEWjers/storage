import { Component, OnInit } from '@angular/core';
import {Sell} from "../dto/Sell";
import {MatDialog} from "@angular/material/dialog";
import {SellService} from "../_services/sell.service";
import {AddSellComponent} from "../add-sell/add-sell.component";
import {ViewSellComponent} from "../view-sell/view-sell.component";
import {PageEvent} from "@angular/material/paginator";
import {Sort} from "@angular/material/sort";

@Component({
  selector: 'app-board-sell',
  templateUrl: './board-sell.component.html',
  styleUrls: ['./board-sell.component.css']
})
export class BoardSellComponent implements OnInit {

  sells?: Sell[];

  totalElements: number = 0;

  currentPage: number = 0;

  currentSize: number = 13;

  currentSort: string = '';

  currentWay: string = '';

  constructor(
    private matDialog: MatDialog,
    private sellService: SellService
  ) { }

  ngOnInit(): void {
    this.sellService.getSellsPage(0, 13, 'id', 'asc').subscribe(
      data => {
        this.sells = data;
      }
    );

    this.sellService.getSellsSize().subscribe(
      data => {
        this.totalElements = data;
      }
    );
  }

  addSellDialog(enterAnimationDuration: string, exitAnimationDuration: string) {
    this.matDialog.open(AddSellComponent, {
      width: '75%',
      enterAnimationDuration,
      exitAnimationDuration
    });
  }

  viewSellDialog(enterAnimationDuration: string, exitAnimationDuration: string, sell:Sell) {
    this.matDialog.open(ViewSellComponent, {
      width: '75%',
      enterAnimationDuration,
      exitAnimationDuration,
      data: {
        sell: sell
      }
    })
  }

  nextPage(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.currentSize = event.pageSize;
    this.sellService.getSellsPage(event.pageIndex, event.pageSize, this.currentSort, this.currentWay).subscribe(
      data => {
        this.sells = data;
      }
    );
  }

  sortData(sort: Sort) {
    this.currentSort = sort.active;
    this.currentWay = sort.direction;
    this.sellService.getSellsPage(this.currentPage, this.currentSize, sort.active, sort.direction).subscribe(
      data => {
        this.sells = data;
      }
    );
  }

}
