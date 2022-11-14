import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AddProducerComponent } from '../add-producer/add-producer.component';
import { Producer } from '../dto/Producer';
import { ProducerService } from '../_services/producer.service';

@Component({
  selector: 'app-board-producer',
  templateUrl: './board-producer.component.html',
  styleUrls: ['./board-producer.component.css']
})
export class BoardProducerComponent implements OnInit {

  producers?: Producer[];

  constructor(
    private matDialog: MatDialog,
    private producerService: ProducerService
  ) { }

  ngOnInit(): void {
      this.producerService.getAllProducers().subscribe(
        data => {
          this.producers = data;
        }
      )
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

}
