export interface ICreateBoardGameRequest {
	barcode: number;
	title: string;
	category: string;
	description: string;
	minPlayers: number;
	maxPlayers: number;
	linkInstructionManual: string;
	linkVideoTutorial: string;
}
