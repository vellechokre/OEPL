import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainComponent } from './main/main.component';
import { PlayersComponent } from './components/players/players.component';
import { BiddingComponent } from './components/bidding/bidding.component';

const routes: Routes = [
  {
    path: '', component: MainComponent,
  },
  {
    path: 'players', component: PlayersComponent
  },
  {
    path: 'bidding', component: BiddingComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
