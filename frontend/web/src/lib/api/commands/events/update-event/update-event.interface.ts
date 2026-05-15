export interface IUpdateEventRequest {
    name: string;
    description: string;
    startDate: Date;
    finalDate: Date;
    street: string;
    number: string;
    supplement: string;
    neighborhood: string;
    city: string;
    state: string;
    zipCode: string;
    hasBoardGames: boolean;
    hasRpg: boolean;
    hasEscapeRoom: boolean;
    gamesIds: string[];
}