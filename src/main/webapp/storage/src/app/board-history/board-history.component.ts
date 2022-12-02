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

  date: string = '';

  user: string = '';

  movingType: string = '';

  code: string = '';

  itemSize: string = '';

  pack: string = '';

  price: string = '';

  description: string = '';

  producer: string = '';

  count: string = '';

  constructor(private movingRecordService: MovingRecordService) { }

  ngOnInit(): void {
    this.movingRecordService.getMovingRecordsPage(0, 13, 'date', 'asc', this.date, this.user, this.movingType, this.code, this.itemSize, this.pack, this.price, this.description, this.producer, this.count).subscribe(
      data => {
        this.movingRecords = data;
      }
    );

    this.movingRecordService.getMovingRecordsSize(this.date, this.user, this.movingType, this.code, this.itemSize, this.pack, this.price, this.description, this.producer, this.count).subscribe(
      data => {
        this.totalElements = data;
      }
    );
  }

  nextPage(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.currentSize = event.pageSize;
    this.movingRecordService.getMovingRecordsPage(event.pageIndex, event.pageSize, this.currentSort, this.currentWay, this.date, this.user, this.movingType, this.code, this.itemSize, this.pack, this.price, this.description, this.producer, this.count).subscribe(
      data => {
        this.movingRecords = data;
      }
    );
  }

  sortData(sort: Sort) {
    this.currentSort = sort.active;
    this.currentWay = sort.direction;
    this.movingRecordService.getMovingRecordsPage(this.currentPage, this.currentSize, sort.active, sort.direction, this.date, this.user, this.movingType, this.code, this.itemSize, this.pack, this.price, this.description, this.producer, this.count).subscribe(
      data => {
        this.movingRecords = data;
      }
    );
  }

  onFilter() {
    this.movingRecordService.getMovingRecordsPage(this.currentPage, this.currentSize, this.currentSort, this.currentWay, this.date, this.user, this.movingType, this.code, this.itemSize, this.pack, this.price, this.description, this.producer, this.count).subscribe(
      data => {
        this.movingRecords = data;
      }
    );

    this.movingRecordService.getMovingRecordsSize(this.date, this.user, this.movingType, this.code, this.itemSize, this.pack, this.price, this.description, this.producer, this.count).subscribe(
      data => {
        this.totalElements = data;
      }
    );
  }

}
