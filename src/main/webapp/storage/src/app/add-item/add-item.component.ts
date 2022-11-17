import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Producer } from '../dto/Producer';
import { ItemService } from '../_services/item.service';
import { ProducerService } from '../_services/producer.service';

@Component({
  selector: 'app-add-item',
  templateUrl: './add-item.component.html',
  styleUrls: ['./add-item.component.css']
})
export class AddItemComponent implements OnInit {

  producers?: Producer[];

  form: any = {
    code: null,
    size: null,
    pack: null,
    price: null,
    description: null,
    producerId: null
  };

  errorMessage = '';

  constructor(
    private dialogRef: MatDialogRef<AddItemComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private producerService: ProducerService,
    private itemService: ItemService
  ) { }

  ngOnInit(): void {
    if (this.data.type == 'update') {
      this.form = {
        code: this.data.code,
        size: this.data.size,
        pack: this.data.pack,
        price: this.data.price,
        description: this.data.description,
        producerId: this.data.producer.id
      };
    }

    this.producerService.getAllProducers().subscribe(
      income => {
        this.producers = income;
      }
    );
  }

  onSubmit() {

    const {code, size, pack, price, description, producerId} = this.form;
    console.log(description);
    

    if (this.data.type == 'create') {
      this.itemService.createItem(code, size, pack, price, description, producerId).subscribe(
        data => {
          this.dialogRef.close();
          window.location.reload();
        },
        err => {
          this.errorMessage = err.error.message;
        }
      );
    }

    if (this.data.type == 'update') {
      this.itemService.updateItem(this.data.id, code, size, pack, price, description, producerId).subscribe(
        data => {
          this.dialogRef.close();
          window.location.reload();
        },
        err => {
          this.errorMessage = err.error.message;
        }
      );
    }
  }

}
