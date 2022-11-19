import { Component, OnInit } from '@angular/core';
import {Record} from "../dto/Record";
import {MatDialog} from "@angular/material/dialog";
import {RecordService} from "../_services/record.service";

@Component({
  selector: 'app-board-record',
  templateUrl: './board-record.component.html',
  styleUrls: ['./board-record.component.css']
})
export class BoardRecordComponent implements OnInit {

  records?: Record[];

  constructor(
    private matDialog: MatDialog,
    private recordService: RecordService
  ) { }

  ngOnInit(): void {
    this.recordService.getAllItems().subscribe(
      data => {
        this.records = data;
      }
    );
  }

}
