import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ArrivalService} from "../_services/arrival.service";

@Component({
  selector: 'app-view-arrival',
  templateUrl: './view-arrival.component.html',
  styleUrls: ['./view-arrival.component.css']
})
export class ViewArrivalComponent {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<ViewArrivalComponent>,
    private arrivalService: ArrivalService
  ) { }

  close() {
    this.dialogRef.close();
  }

  generateReport(id: number) {
    this.arrivalService.getArrivalExistedReport(id).subscribe(
      (response:any) => {
        let fileName: string = 'arrival.pdf';
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
