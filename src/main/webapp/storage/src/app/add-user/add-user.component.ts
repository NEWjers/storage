import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AuthService } from '../_services/auth.service';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Inject } from '@angular/core';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {

  form: any = {
    username: null,
    password: null,
    role: null
  };

  errorMessage = '';

  isNotCurrentUser: boolean = true;

  constructor(
    private authService: AuthService,
    private dialogRef: MatDialogRef<AddUserComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    if(this.data.type == 'update'){
      this.form = {
        username: this.data.username,
        role: this.data.role
      };
    }

    this.isNotCurrentUser = this.data.isNotCurrentUser;
  }

  onSubmit() {
    const { username, password, role } = this.form;

    if (this.data.type == 'create') {
      this.authService.register(username, password, role).subscribe(
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
      this.userService.updateUser(this.data.id, username, role, password).subscribe(
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
