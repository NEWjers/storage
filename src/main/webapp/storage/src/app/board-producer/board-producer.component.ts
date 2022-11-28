import {Component, OnInit} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AddProducerComponent } from '../add-producer/add-producer.component';
import { Producer } from '../dto/Producer';
import { ProducerService } from '../_services/producer.service';
import {TokenStorageService} from "../_services/token-storage.service";
import {PageEvent} from "@angular/material/paginator";
import {Sort} from "@angular/material/sort";

@Component({
  selector: 'app-board-producer',
  templateUrl: './board-producer.component.html',
  styleUrls: ['./board-producer.component.css']
})
export class BoardProducerComponent implements OnInit {

  producers?: Producer[];

  isAdmin: boolean = false;

  totalElements: number = 0;

  currentPage: number = 0;

  currentSize: number = 9;

  currentSort: string = '';

  currentWay: string = '';

  constructor(
    private matDialog: MatDialog,
    private producerService: ProducerService,
    private tokenStorageService: TokenStorageService
  ) { }

  ngOnInit(): void {
      this.producerService.getProducersPage(0,9, 'id', 'asc').subscribe(
        data => {
          this.producers = data;
        }
      );

    this.producerService.getProducersSize().subscribe(
      data => {
        this.totalElements = data;
      }
    );

    const user = this.tokenStorageService.getUser();
    const roles = user.roles;
    this.isAdmin = roles.includes('ROLE_ADMIN');
  }

  addProducerDialog(enterAnimationDuration: string, exitAnimationDuration: string) {
    this.matDialog.open(AddProducerComponent, {
      width: '500px',
      enterAnimationDuration,
      exitAnimationDuration,
      data: {
        type: 'create'
      }
    });
  }

  editProducerDialog(enterAnimationDuration: string, exitAnimationDuration: string, name?: string, country?: string, description?: string, id?: number) {
    this.matDialog.open(AddProducerComponent, {
      width: '500px',
      enterAnimationDuration,
      exitAnimationDuration,
      data: {
        type: 'update',
        name: name,
        country: country,
        description: description,
        id: id
      }
    });
  }

  deleteProducer(id: number) {
    this.producerService.deleteProducer(id).subscribe(
      data => {
        window.location.reload();
      }
    );
  }

  nextPage(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.currentSize = event.pageSize;
    this.producerService.getProducersPage(event.pageIndex, event.pageSize, this.currentSort, this.currentWay).subscribe(
      data => {
        this.producers = data;
      }
    );
  }

  sortData(sort: Sort) {
    this.currentSort = sort.active;
    this.currentWay = sort.direction;
    this.producerService.getProducersPage(this.currentPage, this.currentSize, sort.active, sort.direction).subscribe(
      data => {
        this.producers = data;
      }
    );
  }

}
