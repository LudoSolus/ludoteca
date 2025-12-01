import type { EEventStatus } from "$lib/shared/enums/event-status.enum";

export interface IListEvent {
	id: string;
	name: string;
	status: EEventStatus;
	startDate: Date;
	finalDate: Date;
	street: string;
	number: string;
	neighborhood: string;
	city: string;
	state: string;
	zipCode: string;
}
