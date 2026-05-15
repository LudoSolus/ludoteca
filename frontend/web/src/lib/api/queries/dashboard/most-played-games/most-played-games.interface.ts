export interface IMostPlayedGamesData {
	gameTitle: string;
	loanCount: number;
}

export interface IMostPlayedGamesResponse {
	data: IMostPlayedGamesData[];
}
