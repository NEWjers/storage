import { Component, OnInit } from '@angular/core';
import {MovingRecordService} from "../_services/moving-record.service";
import {MovingRecordResponse} from "../dto/MovingRecordResponse";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-board-history',
  templateUrl: './board-history.component.html',
  styleUrls: ['./board-history.component.css']
})
export class BoardHistoryComponent implements OnInit {

  movingRecords?: MovingRecordResponse[];

  totalElements: number = 0;

  constructor(private movingRecordService: MovingRecordService) { }

  ngOnInit(): void {
    this.movingRecordService.getMovingRecordsPage(0, 13).subscribe(
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
    this.movingRecordService.getMovingRecordsPage(event.pageIndex, event.pageSize).subscribe(
      data => {
        this.movingRecords = data;
      }
    );
  }

}
