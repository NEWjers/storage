import { Component, OnInit } from '@angular/core';
import {Sell} from "../dto/Sell";
import {MatDialog} from "@angular/material/dialog";
import {SellService} from "../_services/sell.service";
import {AddSellComponent} from "../add-sell/add-sell.component";
import {ViewSellComponent} from "../view-sell/view-sell.component";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-board-sell',
  templateUrl: './board-sell.component.html',
  styleUrls: ['./board-sell.component.css']
})
export class BoardSellComponent implements OnInit {

  sells?: Sell[];

  totalElements: number = 0;

  constructor(
    private matDialog: MatDialog,
    private sellService: SellService
  ) { }

  ngOnInit(): void {
    this.sellService.getSellsPage(0, 13).subscribe(
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
    this.sellService.getSellsPage(event.pageIndex, event.pageSize).subscribe(
      data => {
        this.sells = data;
      }
    );
  }

}
