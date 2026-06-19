export interface ISeasonalityData {
	month: number;
	year: number;
	totalLoans: number;
}

export type ISeasonalityResponse = ISeasonalityData[];
