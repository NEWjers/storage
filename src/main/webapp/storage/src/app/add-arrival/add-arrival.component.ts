import { Component, OnInit } from '@angular/core';
import {Item} from "../dto/Item";
import {ItemService} from "../_services/item.service";
import {HtmlTagDefinition} from "@angular/compiler";

@Component({
  selector: 'app-add-arrival',
  templateUrl: './add-arrival.component.html',
  styleUrls: ['./add-arrival.component.css']
})
export class AddArrivalComponent implements OnInit {

  items: Item[] = [];

  selectedItems: Item[] = [];

  searchText = '';

  constructor(private itemService: ItemService) { }

  ngOnInit(): void {
    this.itemService.getAllItems().subscribe(
      data => {
        this.items = data;
      }
    )
  }

  onSubmit() {}

  addItem(item: Item) {
    this.selectedItems.push(item);
    (document.getElementById('search-text') as HTMLInputElement).focus();
  }

}
