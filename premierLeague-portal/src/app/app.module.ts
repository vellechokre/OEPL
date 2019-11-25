import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule, forwardRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { CalendarModule, PanelModule, OrderListModule, CardModule, DropdownModule, ButtonModule } from 'primeng/primeng';
import { DataViewModule } from 'primeng/dataview';
import { MainComponent } from './main/main.component';
import { AlertModule } from 'ngx-bootstrap';
import { AngularFireModule } from '@angular/fire';
import { environment } from '../environments/environment';
import { PlayersComponent } from './components/players/players.component';
import { BiddingComponent } from './components/bidding/bidding.component';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    PlayersComponent,
    BiddingComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    CalendarModule,
    AngularFireModule.initializeApp(environment.firebase),
    DataViewModule,
    PanelModule,
    OrderListModule,
    CardModule,
    DropdownModule,
    ButtonModule,
    AlertModule.forRoot()
  ],
  providers: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
