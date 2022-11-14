import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AuthService } from '../_services/auth.service';
import { ProducerService } from '../_services/producer.service';

@Component({
  selector: 'app-add-producer',
  templateUrl: './add-producer.component.html',
  styleUrls: ['./add-producer.component.css']
})
export class AddProducerComponent implements OnInit {

  form: any = {
    name: null,
    country: null,
    description: null
  };

  errorMessage = '';

  constructor(
    private authService: AuthService,
    private dialogRef: MatDialogRef<AddProducerComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private producerService: ProducerService
  ) { }

  ngOnInit(): void {
    if (this.data.type == 'update') {
      this.form = {
        name: this.data.name,
        country: this.data.country,
        description: this.data.description
      }
    }
  }

  onSubmit() {
    const { name, country, description } = this.form;

    if (this.data.type == 'create') {
      this.producerService.createProducer(name, country, description).subscribe(
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
      console.log("update");

      this.producerService.updateProducer(this.data.id, name, country, description).subscribe(
        data => {
          this.dialogRef.close();
          window.location.reload();
        },
        err => {
          this.errorMessage = err.error.message;
        }
      )
    }
  }

}
