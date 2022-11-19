import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { BoardProducerComponent } from './board-producer/board-producer.component';
import { BoardItemComponent } from './board-item/board-item.component';
import {BoardRecordComponent} from "./board-record/board-record.component";

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'item', component: BoardItemComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'admin', component: BoardAdminComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'producer', component: BoardProducerComponent},
  { path: 'record', component: BoardRecordComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
