import { Component, OnInit } from '@angular/core';
import { PlayersService } from '../../services/players.service';
import { OEPLMember } from '../../modals/OEPLMember';
import { ViewEncapsulation } from '@angular/core';
import { SelectItem } from 'primeng/primeng';
import { OEPLTeam } from '../../modals/OEPLTeam';

@Component({
  selector: 'app-bidding',
  templateUrl: './bidding.component.html',
  styleUrls: ['./bidding.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class BiddingComponent implements OnInit {

  players: OEPLMember[] = [];
  teams: SelectItem[] = [];
  selectedTeam: any;
  selectedPlayer: OEPLMember = new OEPLMember();
  royals: OEPLMember[] = [];
  allTeams: OEPLTeam[] = [];
  isError: boolean = false;
  errorMsg: string;
  constructor(
    private service: PlayersService
  ) { }

  ngOnInit() {

    this.getPlayers();
    this.getTeams();
  }

  getPlayers() {
    this.service.getUnassignedPlayers('members/findAll', false)
      .subscribe((response) => {
        if (response) {
          this.players = response;
        }
      });
  }

  getTeams() {
    this.service.getTeams('members/allTeam')
      .subscribe((response) => {
        if (response) {
          this.allTeams = response;
          this.setTeams(response);
          this.setMaxBidAmount();
        }
      })
  }

  setTeams(data) {
    for (let i = 0; i < data.length; i++) {
      // tslint:disable-next-line:no-var-keyword
      this.teams.push({ label: data[i].teamName, value: data[i].id });
    }
  }

  onPlayerSelection(player) {
    this.selectedPlayer = player;
  }

  setMaxBidAmount(){
    var team: OEPLTeam = this.allTeams.filter((team) => team.id === this.selectedTeam)[0];
    this.allTeams.forEach((team)=>{
      var teamSize = 15;
      if (team.members) {
        teamSize = teamSize - team.members.length;
      }
      if(team.amount <100){
        team.maxBidAmount = 0;
      }else{
      team.maxBidAmount = (team.amount - (teamSize-1) * 100);
      }
    })
  }

  assignPlayerToTeam() {
    if (this.selectedPlayer.price < 100) {
      this.isError = true;
      this.errorMsg = "Price can't be less than 100";
      return;
    } if (!this.selectedTeam) {
      this.isError = true;
      this.errorMsg = "Please select Team";
      return;
    } if (!this.validateBeforeSave()) {

      this.isError = true;
      this.errorMsg = "Team has no sufficient amount to bid";
      return;
    }
    this.isError = false;
    this.errorMsg = "";
    this.service.addPlayer(this.selectedPlayer, 'members/addMember', this.selectedTeam)
      .subscribe((response) => {
        if (response) {
          this.getPlayers();
          this.getTeams();
          this.selectedPlayer = new OEPLMember();
        }
      })
  }

  validateBeforeSave(): boolean {
    var team: OEPLTeam = this.allTeams.filter((team) => team.id === this.selectedTeam)[0];

    if (team.amount < 100){
      return false;
    }

    var teamSize = 15;
    if (team.members) {
      teamSize = teamSize - team.members.length;
    }

    if(team.maxBidAmount<this.selectedPlayer.price){
      return false;
    }else{
      return true;
    }

    // if (((team.amount - (teamSize-1) * 100) > this.selectedPlayer.price)) {
    //   return false;
    // } else {
    //   return true;
    // }
  }

}



export class SITem {
  label: any;
  value: any;
}
