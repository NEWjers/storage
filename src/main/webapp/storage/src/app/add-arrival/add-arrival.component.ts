import { Component, OnInit } from '@angular/core';
import {Item} from "../dto/Item";
import {ItemService} from "../_services/item.service";
import {ArrivalRequest} from "../dto/ArrivalRequest";
import {ArrivalService} from "../_services/arrival.service";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-add-arrival',
  templateUrl: './add-arrival.component.html',
  styleUrls: ['./add-arrival.component.css']
})
export class AddArrivalComponent implements OnInit {

  items: Item[] = [];

  selectedItems: ArrivalRequest[] = [];

  searchText = '';

  form: any;

  constructor(
    private itemService: ItemService,
    private arrivalService: ArrivalService,
    private dialogRef: MatDialogRef<AddArrivalComponent>
  ) { }

  ngOnInit(): void {
    this.itemService.getAllItems().subscribe(
      data => {
        this.items = data;
      }
    )
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
    this.selectedItems.push(new ArrivalRequest('', item));
    (document.getElementById('search-text') as HTMLInputElement).focus();
  }

  trackByIndex(index: number, obj: any): any {
    return index;
  }

}
