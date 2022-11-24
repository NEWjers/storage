import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {LoginComponent} from './login/login.component';
import {ProfileComponent} from './profile/profile.component';
import {BoardAdminComponent} from './board-admin/board-admin.component';
import {BoardProducerComponent} from './board-producer/board-producer.component';
import {BoardItemComponent} from './board-item/board-item.component';
import {BoardRecordComponent} from "./board-record/board-record.component";
import {BoardArrivalComponent} from "./board-arrival/board-arrival.component";
import {BoardSellComponent} from "./board-sell/board-sell.component";
import {BoardHistoryComponent} from "./board-history/board-history.component";

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'item', component: BoardItemComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'admin', component: BoardAdminComponent},
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'producer', component: BoardProducerComponent},
  {path: 'record', component: BoardRecordComponent},
  {path: 'arrival', component: BoardArrivalComponent},
  {path: 'sell', component: BoardSellComponent},
  {path: 'history', component: BoardHistoryComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
