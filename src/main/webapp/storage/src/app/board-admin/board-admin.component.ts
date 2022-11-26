import { Component, OnInit } from '@angular/core';
import { User } from '../dto/User';
import { UserService } from '../_services/user.service';
import { MatDialog } from '@angular/material/dialog';
import { AddUserComponent } from '../add-user/add-user.component';
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-board-admin',
  templateUrl: './board-admin.component.html',
  styleUrls: ['./board-admin.component.css']
})
export class BoardAdminComponent implements OnInit {
  users?: User[];

  totalElements: number = 0;

  constructor(
    private userService: UserService,
    private matDialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.userService.getUsersPage(0, 9).subscribe(
      data => {
        this.users = data;
      }
    );

    this.userService.getUsersSize().subscribe(
      data => {
        this.totalElements = data;
      }
    );
  }

  addUserDialog(enterAnimationDuration: string, exitAnimationDuration: string) {
      this.matDialog.open(AddUserComponent, {
        width: '500px',
        enterAnimationDuration,
        exitAnimationDuration,
        data: {
          type: 'create'
        }
      });
  }

  editUserDialog(enterAnimationDuration: string, exitAnimationDuration: string, username?: string, role?: string, id?: number) {
    this.matDialog.open(AddUserComponent, {
      width: '500px',
      enterAnimationDuration,
      exitAnimationDuration,
      data: {
        type: 'update',
        username: username,
        role: role,
        id: id
      }
    });
  }

  deleteUser(id: number) {
    this.userService.deleteUser(id).subscribe(
      data => {
        window.location.reload();
      }
    );
  }

  nextPage(event: PageEvent) {
    this.userService.getUsersPage(event.pageIndex, event.pageSize).subscribe(
      data => {
        this.users = data;
      }
    );
  }

}
