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

  id: string = '';

  code: string = '';

  itemSize: string = '';

  pack: string = '';

  price: string = '';

  description: string = '';

  producer: string = '';

  count: string = '';

  constructor(
    private matDialog: MatDialog,
    private recordService: RecordService
  ) { }

  ngOnInit(): void {
    this.recordService.getRecordsPage(0, 13, 'id', 'asc', this.id, this.code,
      this.itemSize, this.pack, this.price, this.description, this.producer, this.count).subscribe(
      data => {
        this.records = data;
      }
    );

    this.recordService.getRecordsSize(this.id, this.code, this.itemSize, this.pack, this.price, this.description,
      this.producer, this.count).subscribe(
      data => {
        this.totalElements = data;
      }
    );
  }

  nextPage(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.currentSize = event.pageSize;
    this.recordService.getRecordsPage(event.pageIndex, event.pageSize, this.currentSort, this.currentWay, this.id,
      this.code, this.itemSize, this.pack, this.price, this.description, this.producer, this.count).subscribe(
      data => {
        this.records = data;
      }
    );
  }

  sortData(sort: Sort) {
    this.currentSort = sort.active;
    this.currentWay = sort.direction;
    this.recordService.getRecordsPage(this.currentPage, this.currentSize, sort.active, sort.direction,
      this.id, this.code, this.itemSize, this.pack, this.price, this.description, this.producer, this.count).subscribe(
      data => {
        this.records = data;
      }
    );
  }

  onFilter() {
    this.recordService.getRecordsPage(this.currentPage, this.currentSize, this.currentSort, this.currentWay,
      this.id, this.code, this.itemSize, this.pack, this.price, this.description, this.producer, this.count).subscribe(
      data => {
        this.records = data;
      }
    );

    this.recordService.getRecordsSize(this.id, this.code, this.itemSize, this.pack, this.price, this.description,
      this.producer, this.count).subscribe(
      data => {
        this.totalElements = data;
      }
    );
  }

  generateReport() {
    this.recordService.getRecordsReport(this.currentSort, this.currentWay, this.id, this.code, this.itemSize, this.pack, this.price, this.description, this.producer, this.count).subscribe(
      (response:any) => {
        let fileName: string = 'records.pdf';
        let dataType = response.type;
        let binaryData = [];
        binaryData.push(response);
        let downloadLink = document.createElement('a');
        downloadLink.href = window.URL.createObjectURL(new Blob(binaryData, {type: dataType}));
        if (fileName)
          downloadLink.setAttribute('download', fileName);
        document.body.appendChild(downloadLink);
        downloadLink.click();
      }
    );
  }

}
