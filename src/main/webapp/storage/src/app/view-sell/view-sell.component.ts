import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-view-sell',
  templateUrl: './view-sell.component.html',
  styleUrls: ['./view-sell.component.css']
})
export class ViewSellComponent {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<ViewSellComponent>
  ) { }

  close() {
    this.dialogRef.close();
  }

}
