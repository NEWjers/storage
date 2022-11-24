import { Component, OnInit } from '@angular/core';
import {MovingRecordService} from "../_services/moving-record.service";
import {MovingRecordResponse} from "../dto/MovingRecordResponse";

@Component({
  selector: 'app-board-history',
  templateUrl: './board-history.component.html',
  styleUrls: ['./board-history.component.css']
})
export class BoardHistoryComponent implements OnInit {

  movingRecords?: MovingRecordResponse[];

  constructor(private movingRecordService: MovingRecordService) { }

  ngOnInit(): void {
    this.movingRecordService.getAllMovingRecords().subscribe(
      data => {
        this.movingRecords = data;
        console.log(this.movingRecords)
      }
    )
  }

}
