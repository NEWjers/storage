import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-view-arrival',
  templateUrl: './view-arrival.component.html',
  styleUrls: ['./view-arrival.component.css']
})
export class ViewArrivalComponent {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<ViewArrivalComponent>
  ) { }

  close() {
    this.dialogRef.close();
  }

}
