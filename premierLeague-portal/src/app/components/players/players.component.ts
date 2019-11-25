import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { PlayersService } from '../../services/players.service';
import { OEPLMember } from '../../modals/OEPLMember';

@Component({
  selector: 'app-players',
  templateUrl: './players.component.html',
  styleUrls: ['./players.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class PlayersComponent implements OnInit {

  players: OEPLMember[] = [];
  constructor(
    private service: PlayersService
  ) { }

  ngOnInit() {
    this.service.getPlayers('members/findAll')
      .subscribe((response) => {
        if (response) {
          this.players = response;
        }
      });
  }

}
