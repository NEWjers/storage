import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from '../_services/token-storage.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isAdmin: boolean = false;
  private roles: string[] = [];

  constructor(
    private tokenStorageService: TokenStorageService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    this.roles = user.roles;

    this.isAdmin = this.roles.includes('ROLE_ADMIN')
  }

  logout(): void {
    this.tokenStorageService.signOut();
    this.router.navigate(['login']);
  }

}
