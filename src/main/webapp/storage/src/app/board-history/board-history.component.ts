import { Component, OnInit } from '@angular/core';
import {MovingRecordService} from "../_services/moving-record.service";
import {MovingRecordResponse} from "../dto/MovingRecordResponse";
import {PageEvent} from "@angular/material/paginator";
import {Sort} from "@angular/material/sort";

@Component({
  selector: 'app-board-history',
  templateUrl: './board-history.component.html',
  styleUrls: ['./board-history.component.css']
})
export class BoardHistoryComponent implements OnInit {

  movingRecords?: MovingRecordResponse[];

  totalElements: number = 0;

  currentPage: number = 0;

  currentSize: number = 13;

  currentSort: string = '';

  currentWay: string = '';

  constructor(private movingRecordService: MovingRecordService) { }

  ngOnInit(): void {
    this.movingRecordService.getMovingRecordsPage(0, 13, 'date', 'asc').subscribe(
      data => {
        this.movingRecords = data;
      }
    );

    this.movingRecordService.getMovingRecordsSize().subscribe(
      data => {
        this.totalElements = data;
      }
    );
  }

  nextPage(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.currentSize = event.pageSize;
    this.movingRecordService.getMovingRecordsPage(event.pageIndex, event.pageSize, this.currentSort, this.currentWay).subscribe(
      data => {
        this.movingRecords = data;
      }
    );
  }

  sortData(sort: Sort) {
    this.currentSort = sort.active;
    this.currentWay = sort.direction;
    this.movingRecordService.getMovingRecordsPage(this.currentPage, this.currentSize, sort.active, sort.direction).subscribe(
      data => {
        this.movingRecords = data;
      }
    );
  }

}
