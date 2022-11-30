import { Component, OnInit } from '@angular/core';
import {Item} from "../dto/Item";
import {ArrivalRequest} from "../dto/ArrivalRequest";
import {ItemService} from "../_services/item.service";
import {SellService} from "../_services/sell.service";
import {MatDialogRef} from "@angular/material/dialog";
import {TokenStorageService} from "../_services/token-storage.service";

@Component({
  selector: 'app-add-sell',
  templateUrl: './add-sell.component.html',
  styleUrls: ['./add-sell.component.css']
})
export class AddSellComponent implements OnInit {

  items: Item[] = [];

  selectedItems: ArrivalRequest[] = [];

  searchText = '';

  currentUser: any;

  constructor(
    private itemService: ItemService,
    private sellService: SellService,
    private dialogRef: MatDialogRef<AddSellComponent>,
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
    this.sellService.createSell(this.selectedItems).subscribe(
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

}
