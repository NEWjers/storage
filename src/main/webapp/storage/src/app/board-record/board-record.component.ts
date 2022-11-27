import { Component, OnInit } from '@angular/core';
import {Record} from "../dto/Record";
import {MatDialog} from "@angular/material/dialog";
import {RecordService} from "../_services/record.service";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-board-record',
  templateUrl: './board-record.component.html',
  styleUrls: ['./board-record.component.css']
})
export class BoardRecordComponent implements OnInit {

  records?: Record[];

  totalElements: number = 0;

  constructor(
    private matDialog: MatDialog,
    private recordService: RecordService
  ) { }

  ngOnInit(): void {
    this.recordService.getRecordsPage(0, 13).subscribe(
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
    this.recordService.getRecordsPage(event.pageIndex, event.pageSize).subscribe(
      data => {
        this.records = data;
      }
    );
  }

}
