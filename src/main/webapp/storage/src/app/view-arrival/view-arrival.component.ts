import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Arrival} from "../dto/Arrival";

@Component({
  selector: 'app-view-arrival',
  templateUrl: './view-arrival.component.html',
  styleUrls: ['./view-arrival.component.css']
})
export class ViewArrivalComponent implements OnInit {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<ViewArrivalComponent>
  ) { }

  ngOnInit(): void {
  }

  close() {
    this.dialogRef.close();
  }

}
