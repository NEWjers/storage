import { Component, OnInit } from '@angular/core';
import {Item} from "../dto/Item";
import {ItemService} from "../_services/item.service";
import {ArrivalRequest} from "../dto/ArrivalRequest";
import {ArrivalService} from "../_services/arrival.service";
import {MatDialogRef} from "@angular/material/dialog";
import {TokenStorageService} from "../_services/token-storage.service";

@Component({
  selector: 'app-add-arrival',
  templateUrl: './add-arrival.component.html',
  styleUrls: ['./add-arrival.component.css']
})
export class AddArrivalComponent implements OnInit {

  items: Item[] = [];

  selectedItems: ArrivalRequest[] = [];

  searchText = '';

  currentUser: any;

  constructor(
    private itemService: ItemService,
    private arrivalService: ArrivalService,
    private dialogRef: MatDialogRef<AddArrivalComponent>,
    private token: TokenStorageService
  ) { }

  ngOnInit(): void {
    this.itemService.getAllItems().subscribe(
      data => {
        this.items = data;
      }
    );

    this.currentUser = this.token.getUser();
  }

  onSubmit() {
    this.arrivalService.createArrival(this.selectedItems).subscribe(
      data => {
        this.dialogRef.close();
        window.location.reload();
      }
    );
  }

  addItem(item: Item) {
    this.selectedItems.push(new ArrivalRequest('', this.currentUser.username, item));
    (document.getElementById('search-text') as HTMLInputElement).focus();
  }

  trackByIndex(index: number, obj: any): any {
    return index;
  }

  close() {
    this.dialogRef.close();
  }

  generateReport() {
    this.arrivalService.getArrivalNewReport(this.selectedItems).subscribe(
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
