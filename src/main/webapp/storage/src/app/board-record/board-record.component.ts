import { Component, OnInit } from '@angular/core';
import {Record} from "../dto/Record";
import {MatDialog} from "@angular/material/dialog";
import {RecordService} from "../_services/record.service";
import {PageEvent} from "@angular/material/paginator";
import {Sort} from "@angular/material/sort";

@Component({
  selector: 'app-board-record',
  templateUrl: './board-record.component.html',
  styleUrls: ['./board-record.component.css']
})
export class BoardRecordComponent implements OnInit {

  records?: Record[];

  totalElements: number = 0;

  currentPage: number = 0;

  currentSize: number = 13;

  currentSort: string = '';

  currentWay: string = '';

  constructor(
    private matDialog: MatDialog,
    private recordService: RecordService
  ) { }

  ngOnInit(): void {
    this.recordService.getRecordsPage(0, 13, 'id', 'asc').subscribe(
      data => {
        this.records = data;
      }
    );

    this.recordService.getRecordsSize().subscribe(
      data => {
        this.totalElements = data;
      }
    );
  }

  nextPage(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.currentSize = event.pageSize;
    this.recordService.getRecordsPage(event.pageIndex, event.pageSize, this.currentSort, this.currentWay).subscribe(
      data => {
        this.records = data;
      }
    );
  }

  sortData(sort: Sort) {
    this.currentSort = sort.active;
    this.currentWay = sort.direction;
    this.recordService.getRecordsPage(this.currentPage, this.currentSize, sort.active, sort.direction).subscribe(
      data => {
        this.records = data;
      }
    );
  }

}
