import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';

import { authInterceptorProviders } from './_helpers/auth.interceptor';
import { HeaderComponent } from './header/header.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialogModule } from '@angular/material/dialog';
import { AddUserComponent } from './add-user/add-user.component';
import { BoardProducerComponent } from './board-producer/board-producer.component';
import { AddProducerComponent } from './add-producer/add-producer.component';
import { BoardItemComponent } from './board-item/board-item.component';
import { AddItemComponent } from './add-item/add-item.component';
import { NgMaterialModule } from './ng-material/ng-material.module';
import { BoardRecordComponent } from './board-record/board-record.component';
import { BoardArrivalComponent } from './board-arrival/board-arrival.component';
import { AddArrivalComponent } from './add-arrival/add-arrival.component';
import { FilterPipe } from './pipes/filter.pipe';
import { HighlightDirective } from "./directives/highlight.directive";
import { ViewArrivalComponent } from './view-arrival/view-arrival.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ProfileComponent,
    BoardAdminComponent,
    HeaderComponent,
    AddUserComponent,
    BoardProducerComponent,
    AddProducerComponent,
    BoardItemComponent,
    AddItemComponent,
    BoardRecordComponent,
    BoardArrivalComponent,
    AddArrivalComponent,
    FilterPipe,
    HighlightDirective,
    ViewArrivalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatDialogModule,
    NgMaterialModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
