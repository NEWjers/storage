import {Component, OnInit} from '@angular/core';
import {User} from '../dto/User';
import {UserService} from '../_services/user.service';
import {MatDialog} from '@angular/material/dialog';
import {AddUserComponent} from '../add-user/add-user.component';
import {PageEvent} from "@angular/material/paginator";
import {Sort} from "@angular/material/sort";

@Component({
  selector: 'app-board-admin',
  templateUrl: './board-admin.component.html',
  styleUrls: ['./board-admin.component.css']
})
export class BoardAdminComponent implements OnInit {
  users?: User[];

  totalElements: number = 0;

  currentPage: number = 0;

  currentSize: number = 9;

  currentSort: string = '';

  currentWay: string = '';

  constructor(
    private userService: UserService,
    private matDialog: MatDialog
  ) {
  }

  ngOnInit(): void {
    this.userService.getUsersPage(0, 9, 'id', 'asc').subscribe(
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
    this.currentPage = event.pageIndex;
    this.currentSize = event.pageSize;
    this.userService.getUsersPage(event.pageIndex, event.pageSize, this.currentSort, this.currentWay).subscribe(
      data => {
        this.users = data;
      }
    );
  }

  sortData(sort: Sort) {
    this.currentSort = sort.active;
    this.currentWay = sort.direction;
    return this.userService.getUsersPage(this.currentPage, this.currentSize, sort.active, sort.direction).subscribe(
      data => {
        this.users = data;
      }
    );
  }

}
