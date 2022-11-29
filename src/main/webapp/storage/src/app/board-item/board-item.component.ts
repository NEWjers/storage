import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {AddItemComponent} from '../add-item/add-item.component';
import {Item} from '../dto/Item'
import {Producer} from '../dto/Producer';
import {ItemService} from '../_services/item.service';
import {TokenStorageService} from "../_services/token-storage.service";
import {PageEvent} from "@angular/material/paginator";
import {Sort} from "@angular/material/sort";

@Component({
  selector: 'app-board-item',
  templateUrl: './board-item.component.html',
  styleUrls: ['./board-item.component.css']
})
export class BoardItemComponent implements OnInit {

  items?: Item[];

  isAdmin: boolean = false;

  totalElements: number = 0;

  currentPage: number = 0;

  currentSize: number = 9;

  currentSort: string = '';

  currentWay: string = '';

  constructor(
    private matDialog: MatDialog,
    private itemService: ItemService,
    private tokenStorageService: TokenStorageService
  ) {
  }

  ngOnInit(): void {
    this.itemService.getItemsPage(0,9, 'code', 'asc').subscribe(
      data => {
        this.items = data;
      }
    );

    this.itemService.getItemsSize().subscribe(
      data => {
        this.totalElements = data;
      }
    );

    const user = this.tokenStorageService.getUser();
    const roles = user.roles;
    this.isAdmin = roles.includes('ROLE_ADMIN')
  }

  addItemDialog(enterAnimationDuration: string, exitAnimationDuration: string) {
    this.matDialog.open(AddItemComponent, {
      width: '500px',
      enterAnimationDuration,
      exitAnimationDuration,
      data: {
        type: 'create'
      }
    });
  }

  editItemDialog(enterAnimationDuration: string, exitAnimationDuration: string, id: number, code: string, size: string, pack: number, price: string, description: string, producer: Producer) {
    this.matDialog.open(AddItemComponent, {
      width: '500px',
      enterAnimationDuration,
      exitAnimationDuration,
      data: {
        type: 'update',
        id: id,
        code: code,
        size: size,
        pack: pack,
        price: price,
        description: description,
        producer: producer
      }
    })
  }

  deleteItem(id: number) {
    this.itemService.deleteItem(id).subscribe(
      data => {
        window.location.reload();
      }
    );
  }

  nextPage(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.currentSize = event.pageSize;
    this.itemService.getItemsPage(event.pageIndex, event.pageSize, this.currentSort, this.currentWay).subscribe(
      data => {
        this.items = data;
      }
    );
  }

  sortData(sort: Sort) {
    this.currentSort = sort.active;
    this.currentWay = sort.direction;
    this.itemService.getItemsPage(this.currentPage, this.currentSize, sort.active, sort.direction).subscribe(
      data => {
        this.items = data;
      }
    );
  }

}
