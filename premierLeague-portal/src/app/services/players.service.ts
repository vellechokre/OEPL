import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { OEPLMember } from '../modals/OEPLMember';
import { OEPLTeam } from '../modals/OEPLTeam';

@Injectable({
  providedIn: 'root'
})
export class PlayersService {
  baseUrl: string = environment.url;
  constructor(
    private http: HttpClient
  ) { }

  getPlayers(subpath) {
    return this.http.get<OEPLMember[]>(this.baseUrl + subpath);
  }

  addPlayer(player, subpath, id) {
    return this.http.post(this.baseUrl + subpath, player, {
      params: {
        teamId: id
      }
    })
  }

  getTeams(subpath) {
    return this.http.get<OEPLTeam[]>(this.baseUrl + subpath);
  }

  getUnassignedPlayers(subpath, param) {
    return this.http.get<OEPLMember[]>(this.baseUrl + subpath, {
      params: {
        assigned: param
      }
    });
  }
}
