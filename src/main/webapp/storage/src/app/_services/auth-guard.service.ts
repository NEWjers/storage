import { Injectable } from '@angular/core';
import {CanActivate} from "@angular/router";
import {TokenStorageService} from "./token-storage.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(
    private tokenStorageService: TokenStorageService
  ) { }

  canActivate(): boolean {
    const user = this.tokenStorageService.getUser();
    const roles = user.roles;

    return roles.includes('ROLE_ADMIN');
  }
}
