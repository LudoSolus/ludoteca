export interface IParticipantsByEventData {
	eventName: string;
	participantCount: number;
}

export interface IParticipantsByEventResponse {
	data: IParticipantsByEventData[];
}
