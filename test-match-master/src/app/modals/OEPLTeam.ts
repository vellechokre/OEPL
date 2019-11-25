import { OEPLMember } from './OEPLMember';


export class OEPLTeam {
    id: number;
    teamName: string;
    captain: OEPLMember = new OEPLMember();
    members: OEPLMember[] = [];
    amount: number;
}